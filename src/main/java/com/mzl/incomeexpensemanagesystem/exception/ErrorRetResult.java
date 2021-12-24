package com.mzl.incomeexpensemanagesystem.exception;

import com.alibaba.fastjson.JSONObject;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName :   ErrorRetResult
 * @Description: 异常返回信息结果类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 20:45
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRetResult implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;


    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private Object data;
    @ApiModelProperty(value = "返回时间")
    private String datetime;
    @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    public ErrorRetResult(RetCodeEnum retCodeEnum) {
        this.code = retCodeEnum.getCode();
        this.msg = retCodeEnum.getMessage();
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 通用属性设置
     */
    public void setErrorRetResult() {
        Date date = new Date();
        this.datetime = sdf.format(date);
        this.timestamp = date.getTime();
    }

    /**
     * 失败异常  使用枚举返回码
     */
    public static ErrorRetResult error(RetCodeEnum retCodeEnum) {
        ErrorRetResult result = new ErrorRetResult();
        result.setErrorRetResult();
        result.setCode(retCodeEnum.getCode());
        result.setMsg(retCodeEnum.getMessage());
        result.setData(null);
        return result;
    }

    /**
     * 失败异常  有返回码+返回信息
     */
    public static ErrorRetResult error(Integer code, String msg) {
        ErrorRetResult result = new ErrorRetResult();
        result.setErrorRetResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 失败异常  仅有返回信息
     */
    public static ErrorRetResult error(String msg) {
        ErrorRetResult result = new ErrorRetResult();
        result.setErrorRetResult();
        result.setCode(500);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
