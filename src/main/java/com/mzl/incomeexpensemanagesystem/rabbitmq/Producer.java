package com.mzl.incomeexpensemanagesystem.rabbitmq;

/**
 * @ClassName :   Producer
 * @Description: 生产者，推送消息对消息队列
 * @Author: v_ktlema
 * @CreateDate: 2021/12/27 20:36
 * @Version: 1.0
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 邮箱队列名称
     */
    private static final String EMAIL_QUEUE= "email_queue";

    /**
     * 手机号队列名称
     */
    private static final String PHONE_QUEUE= "phone_queue";

    /**
     * 发送邮箱消息到队列
     */
    public void sendEmailToQueue(String email){
        log.info("发送邮箱消息到队列=====>" + "邮箱生产者推送数据到发送邮箱队列..." + new Date());
        // 第一个参数是email_queue队列名称
        amqpTemplate.convertAndSend(EMAIL_QUEUE, email);
        log.info("发送邮箱消息到队列=====>" + "邮箱生产者推送数据成功...");
    }

    /**
     * 发送电话号码到消息到队列
     */
    public void sendPhoneToQueue(String phone){
        log.info("发送电话号码到消息到队列=====>" + "电话号码生产者推送数据到发送邮箱队列..." + new Date());
        // 第一个参数是phone_queue队列名称
        amqpTemplate.convertAndSend(PHONE_QUEUE, phone);
        log.info("发送电话号码到消息到队列=====>" + "电话号码生产者推送数据成功...");
    }

}
