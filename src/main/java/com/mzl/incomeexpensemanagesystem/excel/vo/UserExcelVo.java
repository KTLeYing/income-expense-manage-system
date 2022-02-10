package com.mzl.incomeexpensemanagesystem.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mzl.incomeexpensemanagesystem.excel.converter.BannedConverter;
import com.mzl.incomeexpensemanagesystem.excel.converter.DeletedConverter;
import com.mzl.incomeexpensemanagesystem.excel.converter.SexConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName :   UserExcelVo
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2022/2/8 0:45
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UserExcelVo对象", description="用户导出Excel参数类表")
public class UserExcelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户自增id")
    @TableId(value = "user_id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    @ExcelProperty(value = "用户名", index = 0)
    @ColumnWidth(15)
    private String username;

    @ApiModelProperty(value = "昵称")
    @ExcelProperty(value = "昵称", index = 1)
    @ColumnWidth(15)
    private String name;

    @ApiModelProperty(value = "密码")
    @ExcelIgnore
    private String password;

    @ApiModelProperty(value = "性别，1:男 2:女")
    @ExcelProperty(value = "性别", index = 2, converter = SexConverter.class)
    @ColumnWidth(15)
    private Integer sex;

    @ApiModelProperty(value = "邮箱")
    @ExcelProperty(value = "邮箱", index = 3)
    @ColumnWidth(15)
    private String email;

    @ApiModelProperty(value = "手机号")
    @ExcelProperty(value = "手机号", index = 4)
    @ColumnWidth(15)
    private String phone;

    @ApiModelProperty(value = "头像路径")
    @ExcelProperty(value = "头像路径", index = 5)
    @ColumnWidth(15)
    private String avatarPath;

    @ApiModelProperty(value = "是否已经删除，1：未删除  0: 已删除")
    @ExcelProperty(value = "是否已经删除", index = 6, converter = DeletedConverter.class)
    @ColumnWidth(15)
    private Boolean deleted;

    @ApiModelProperty(value = "是否已经被禁用，1:未被禁用   2: 已被禁用")
    @ExcelProperty(value = "是否已经被禁用", index = 7, converter = BannedConverter.class)
    @ColumnWidth(15)
    private Integer banned;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "注册时间", index = 8)
    @ColumnWidth(15)
    private Date createTime;

    @ApiModelProperty(value = "最近一次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "最近一次登录时间", index = 9)
    @ColumnWidth(15)
    private Date lastLoginTime;

}
