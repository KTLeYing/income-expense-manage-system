package com.mzl.incomeexpensemanagesystem.enums;

import lombok.Getter;

/**
 * @EnumName :   RetCodeEnum
 * @Description: 返回结果返回码枚举
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 20:16
 * @Version: 1.0
 */
@Getter
public enum RetCodeEnum {

    //通用枚举
    SUCCESS(200, "成功"),
    FAILURE(500, "失败"),
    NULL_POINT_EXCEPTION(501, "空指针异常"),

    //用户枚举
    REGISTER_SUCCESS(201, "用户注册成功"),
    REGISTER_FAIL(202, "用户注册失败"),
    LOGIN_SUCCESS(203, "登录成功"),
    LOGIN_FAIL(204, "登录失败"),
    USERNAME_ERROR(205, "用户名错误"),
    PASSWORD_ERROR(206, "密码错误"),
    GRAPHIC_CODE_ERROR(207, "登录验证码错误"),
    VERIFY_CODE_INVALID(208, "验证码失效"),
    REFUSE_REQUEST(210, "请求过于频繁~"),
    TOKEN_EXPIRED(211, "token过期"),
    REQUEST_INVALID(212, "请求无效"),
    TOKEN_ERROR(213, "token不能为空或格式不正确"),
    LOGOUT_SUCCESS(214, "退出登录成功"),
    LOGOUT_FAIL(215, "退出登录失败"),
    USERNAME_EMAIL_PHONE_EXIST(216, "用户名或手机号或邮箱已存在"),
    TWO_NEW_PASSWORD_NOT_SAME(217, "两次新密码不相同"),
    OLD_PASSWORD_ERROR(218, "旧密码不正确"),
    MESSAGE_CODE_ERROR(219, "短信验证码不正确"),
    EMAIL_CODE_ERROR(220, "邮箱验证码不正确"),
    USERNAME_BANNED(205, "用户名被禁用"),
    USER_NULL(206, "用户不存在"),

    //验证码枚举
    SEND_EMAIL_FAIL(301, "邮件发送失败"),
    SEND_MESSAGE_FAIL(302, "短信发送失败"),

    //收支枚举类
    SON_CATEGORY_EXISTS(401, "子类型已存在"),

    //预算枚举类
    DATA_EMPTY(501, "当前数据为空"),

    //新闻模块枚举
    USER_COLLECT_NEWS(601, "用户已收藏此新闻"),
    USER_UN_COLLECTED_NEWS(602, "用户未收藏此新闻"),

    //管理员枚举
    ADMIN_ADD_SUCCESS(701, "管理员添加成功"),
    ADMIN_ADD_FAIL(702, "管理员添加失败"),

    ;

    private Integer code;
    private String message;

    RetCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (RetCodeEnum item : RetCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (RetCodeEnum item : RetCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

}