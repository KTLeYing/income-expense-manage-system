package com.mzl.incomeexpensemanagesystem.exception;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import lombok.Data;

/**
 * @ClassName :   CustomException
 * @Description: 自定义异常类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 20:43
 * @Version: 1.0
 */
@Data
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 4564124491192825748L;

    //错误码
    private int code;

    public CustomException() {
        super();
    }

    /**
     * 定义返回信息
     * @param message
     */
    public CustomException(String message) {
        super(message);
    }

    /**
     * 定义状态码+返回信息
     * @param code
     * @param message
     */
    public CustomException(int code, String message) {
        super(message); //把自定义的message传递个异常父类
        this.setCode(code);
    }

    /**
     * 使用枚举返回码
     * @param retCodeEnum
     */
    public CustomException(RetCodeEnum retCodeEnum) {
        super(retCodeEnum.getMessage()); //把自定义的message传递个异常父类
        this.setCode(retCodeEnum.getCode());
    }

    @Override
    public String toString() {
        return "Exception{" + "code=" + code + ", message=" + this.getMessage() + '}';
    }

}
