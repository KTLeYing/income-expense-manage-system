package com.mzl.incomeexpensemanagesystem.controller.common;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.exception.CustomException;
import com.mzl.incomeexpensemanagesystem.rabbitmq.Producer;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.utils.EmailCodeUtil;
import com.mzl.incomeexpensemanagesystem.utils.SendMessageUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   CodeController
 * @Description: 各种验证码控制器
 * @Author: v_ktlema
 * @CreateDate: 2021/12/23 16:08
 * @Version: 1.0
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/code")
@Api(value = "验证码模块接口", tags = "验证码模块接口")
@Slf4j
public class CodeController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Producer producer;

    /**
     * 用户登录的图文验证码的key前缀
     */
    private static final String GRAPHIC_CODE_KEY_PREFIX = "incomeExpense:graphicCode:";

    /**
     * 用户找回密码的短信验证码的key前缀
     */
    private static final String MESSAGE_CODE_KEY_PREFIX = "incomeExpense:messageCode:";

    /**
     * 用户注册的邮箱验证码的key前缀
     */
    private static final String EMAIL_CODE_KEY_PREFIX = "incomeExpense:emailCode:";

    @GetMapping("/createCaptcha")
    @ApiOperation(value = "生成图形验证码")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GifCaptcha gifCaptcha = new GifCaptcha(130, 64, 4);
        String code = gifCaptcha.text();
        log.info("生成图文验证码：" + code);
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
        //存入session
        //request.getSession().setAttribute("checkCode", code);
        //存入到redis并设置过期时间为180s
        redisTemplate.opsForValue().set(GRAPHIC_CODE_KEY_PREFIX + ip, code, 180, TimeUnit.SECONDS);
        //显示到前端页面
        CaptchaUtil.out(gifCaptcha, request, response);
    }

    @GetMapping("/createMessage")
    @ApiOperation(value = "生成短信验证码")
    public void createMessage(String phone) throws IOException {
        //发送短信
        try {
//            SendMessageUtil.sendSMS(phone);
            //推送电话号码到消息队列
            producer.sendPhoneToQueue(phone);
        } catch (Exception e) {
            e.printStackTrace();
            new CustomException(RetCodeEnum.SEND_MESSAGE_FAIL);
        }

//        //获取远程机器ip
//        String ip = "";
//        try {
//            //先用本地地址模拟
//            ip = InetAddress.getLocalHost().getHostAddress();
//            //获取远程地址(无代理)
////            ip = request.getRemoteAddr();
//            //获取远程地址(Nginx代理), 获取nginx转发的实际ip，前端要在请求头配置X-Real-IP的请求头字段（从请求头中获取，如果是在Nginx设置的话要配置一些东西）
////            ip = request.getHeader("X-Real-IP");
//            log.info("ip: " + ip);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //存储验证码到redis,tts为3分钟
//        Integer code = SendMessageUtil.getCode();
//        log.info("生成短信验证码：" + code);
//        redisTemplate.opsForValue().set(MESSAGE_CODE_KEY_PREFIX + ip, code, 180, TimeUnit.SECONDS);
    }

    @GetMapping("/createEmail")
    @ApiOperation(value = "生成邮箱验证码")
    public void createEmail(String email) throws IOException, MessagingException {
        //发送邮件验证码
//        String code = "";
        try {
//            EmailCodeUtil emailCodeUtil = new EmailCodeUtil();
//            code = emailCodeUtil.sendEmail(email);
            //推送邮件到消息队列
            producer.sendEmailToQueue(email);
        } catch (Exception e) {
            e.printStackTrace();
            new CustomException(RetCodeEnum.SEND_EMAIL_FAIL);
        }
//        //获取远程机器ip
//        String ip = "";
//        try {
//            //先用本地地址模拟
//            ip = InetAddress.getLocalHost().getHostAddress();
//            //获取远程地址(无代理)
////            ip = request.getRemoteAddr();
//            //获取远程地址(Nginx代理), 获取nginx转发的实际ip，前端要在请求头配置X-Real-IP的请求头字段（从请求头中获取，如果是在Nginx设置的话要配置一些东西）
////            ip = request.getHeader("X-Real-IP");
//            log.info("ip: " + ip);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //存储验证码到redis,tts为3分钟
//        log.info("生成邮箱验证码：" + code);
//        redisTemplate.opsForValue().set(EMAIL_CODE_KEY_PREFIX + ip, code, 180, TimeUnit.SECONDS);
    }

}
