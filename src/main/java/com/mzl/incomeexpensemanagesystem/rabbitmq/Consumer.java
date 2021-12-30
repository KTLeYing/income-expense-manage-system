package com.mzl.incomeexpensemanagesystem.rabbitmq;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.exception.CustomException;
import com.mzl.incomeexpensemanagesystem.utils.EmailCodeUtil;
import com.mzl.incomeexpensemanagesystem.utils.SendMessageUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   Consumer
 * @Description: 消费者，监听队列并消费处理消息
 * @Author: v_ktlema
 * @CreateDate: 2021/12/27 20:36
 * @Version: 1.0
 */
@Component
@Slf4j
public class Consumer {

    /**
     * 邮箱队列名称
     */
    private static final String EMAIL_QUEUE= "email_queue";

    /**
     * 手机号队列名称
     */
    private static final String PHONE_QUEUE= "phone_queue";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户找回密码的短信验证码的key前缀
     */
    private static final String MESSAGE_CODE_KEY_PREFIX = "incomeExpense:messageCode:";

    /**
     * 用户注册的邮箱验证码的key前缀
     */
    private static final String EMAIL_CODE_KEY_PREFIX = "incomeExpense:emailCode:";


    /**
     * 邮箱队列消费者，接收邮箱并发送邮箱，方法参数类型要与生产者发送的消息参数类型一致
     * @param email
     */
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(EMAIL_QUEUE))
//    @RabbitListener(queuesToDeclare = {@Queue(EMAIL_QUEUE)})  //监听多个队列
    private void receiveEmail(String email){
        log.info("接收到消费邮箱队列消息：" + email + "..." + new Date());
        //获取远程机器ip
        String ip = "";
        try {
            //先用本地地址模拟
            ip = InetAddress.getLocalHost().getHostAddress();
            //获取远程地址(无代理)
//            ip = request.getRemoteAddr();
            //获取远程地址(Nginx代理), 获取nginx转发的实际ip，前端要在请求头配置X-Real-IP的请求头字段（从请求头中获取，如果是在Nginx设置的话要配置一些东西）
//            ip = request.getHeader("X-Real-IP");
            log.info("ip: " + ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String code = "";
        try {
            EmailCodeUtil emailCodeUtil = new EmailCodeUtil();
            code = emailCodeUtil.sendEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            new CustomException(RetCodeEnum.SEND_EMAIL_FAIL);
        }
        //存储验证码到redis,tts为3分钟
        log.info("生成邮箱验证码：" + code);
        redisTemplate.opsForValue().set(EMAIL_CODE_KEY_PREFIX + ip, code, 180, TimeUnit.SECONDS);
    }

    /**
     * 电话号码队列消费者，接收手机号并发送短信，方法参数类型要与生产者发送的消息参数类型一致
     * @param phone
     */
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(PHONE_QUEUE))
//    @RabbitListener(queuesToDeclare = {@Queue(PHONE_QUEUE)})  //监听多个队列
    private void receivePhone(String phone, Channel channel, Message message) throws IOException {
        log.info("接收到消费电话号码队列消息：" + phone + "..." + new Date());
        //获取远程机器ip
        String ip = "";
        try {
            //先用本地地址模拟
            ip = InetAddress.getLocalHost().getHostAddress();
            //获取远程地址(无代理)
//            ip = request.getRemoteAddr();
            //获取远程地址(Nginx代理), 获取nginx转发的实际ip，前端要在请求头配置X-Real-IP的请求头字段（从请求头中获取，如果是在Nginx设置的话要配置一些东西）
//            ip = request.getHeader("X-Real-IP");
            log.info("ip: " + ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发送短信
        try {
            SendMessageUtil.sendSMS(phone);
        } catch (Exception e) {
            e.printStackTrace();
            new CustomException(RetCodeEnum.SEND_MESSAGE_FAIL);
        }
        //存储验证码到redis,tts为3分钟
        Integer code = SendMessageUtil.getCode();
        log.info("生成短信验证码：" + code);
        redisTemplate.opsForValue().set(MESSAGE_CODE_KEY_PREFIX + ip, code, 180, TimeUnit.SECONDS);

    }

}
