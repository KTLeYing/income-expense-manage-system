package com.mzl.incomeexpensemanagesystem.utils;

/**
 * @ClassName :   HtmlTextUtil
 * @Description: 创建HTML文本工具类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/27 16:56
 * @Version: 1.0
 */
public class HtmlTextUtil {

    //创建邮件发送的HTML文本内容
    public static String htmlContent(String code) {
        //返回携带6位邮箱验证码的HTML内容
        String htmlContent = "Email地址的验证码<br/>" +
                "这封邮件是由【MZL】发送的。<br/>" +
                "您收的的这封邮件是进行【智能收支管理平台】的新用户注册时使用的这个地址。<br/>" +
                "账号激活声明：<br/>" +
                "请将下面的验证码输入到验证码输入框即可：<h3 style='color: red;'>" + code + "</h3>"  +
                "请注意！注册验证码3分钟内有效；请勿回复此邮箱！<br/><br/>";
        return htmlContent;
    }

}
