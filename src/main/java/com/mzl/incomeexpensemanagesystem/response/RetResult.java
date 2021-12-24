package com.mzl.incomeexpensemanagesystem.response;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName :   RetResult
 * @Description: 返回结果封装
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 20:39
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetResult implements Serializable {

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

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 通用属性设置
     * @param retCodeEnum
     */
    public void setRetCodeEnum(RetCodeEnum retCodeEnum) {
        Date date = new Date();
        this.code = retCodeEnum.code();
        this.msg = retCodeEnum.message();
        this.datetime = sdf.format(date);
        this.timestamp = date.getTime();
    }

    /**
     *  成功 不返回数据 返回默认成功状态码
     */
    public static RetResult success() {
        RetResult result = new RetResult();
        result.setRetCodeEnum(RetCodeEnum.SUCCESS);
        return result;
    }

    /**
     * 成功 返回默认成功状态码 并且加上返回数据
     */
    public static RetResult success(Object data) {
        RetResult result = new RetResult();
        result.setRetCodeEnum(RetCodeEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 成功 自定义成功返回状态码 不加数据
     * @param RetCodeEnum
     * @return
     */
    public static RetResult success(RetCodeEnum RetCodeEnum) {
        RetResult result = new RetResult();
        result.setRetCodeEnum(RetCodeEnum);
        return result;
    }

    /**
     * 成功 自定义成功返回状态码 加上数据
     * @param RetCodeEnum
     * @param data
     * @return
     */
    public static RetResult success(RetCodeEnum RetCodeEnum, Object data) {
        RetResult result = new RetResult();
        result.setRetCodeEnum(RetCodeEnum);
        result.setData(data);
        return result;
    }

    /**
     * 失败 返回默认失败的状态码 不返回数据
     * @return
     */
    public static RetResult fail() {
        RetResult result = new RetResult();
        result.setRetCodeEnum(RetCodeEnum.FAILURE);
        return result;
    }

    /**
     * 失败 单返回自定义失败的状态码
     * @param RetCodeEnum
     * @return
     */
    public static RetResult fail(RetCodeEnum RetCodeEnum) {
        RetResult result = new RetResult();
        result.setRetCodeEnum(RetCodeEnum);
        return result;
    }

    /**
     * 失败 返回自定义失败的状态码 + 数据
     * @param RetCodeEnum
     * @param data
     * @return
     */
    public static RetResult fail(RetCodeEnum RetCodeEnum, Object data) {
        RetResult result = new RetResult();
        result.setRetCodeEnum(RetCodeEnum);
        result.setData(data);
        return result;
    }

}
