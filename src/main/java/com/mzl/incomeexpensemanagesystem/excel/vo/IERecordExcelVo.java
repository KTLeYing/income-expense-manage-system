package com.mzl.incomeexpensemanagesystem.excel.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName :   RecordExcelVo
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2022/2/7 15:39
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("i_e_record")
@ApiModel(value="IERecordExcelVo对象", description="收支记录导出表格参数类表")
public class IERecordExcelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收支记录自增id")
    @TableId(value = "i_e_record_id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer iERecordId;

    @ApiModelProperty(value = "用户id")
    @ExcelIgnore
    private Integer userId;

    @ApiModelProperty(value = "收支类型id")
    @ExcelIgnore
    private Integer iECategoryId;

    @ApiModelProperty(value = "收支数量(钱数)")
    @ExcelProperty(value = "收支数量", index = 2)
    @ColumnWidth(15)
    private Double num;

    @ApiModelProperty(value = "收支备注")
    @ExcelProperty(value = "收支备注", index = 3)
    @ColumnWidth(15)
    private String note;

    @ApiModelProperty(value = "记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "记录时间", index = 4)
    @ColumnWidth(15)
    private Date createTime;

    @ApiModelProperty(value = "父收支类型")
    @ExcelProperty(value = "父收支类型", index = 0)
    @ColumnWidth(15)
    private String parentCategory;

    @ApiModelProperty(value = "子收支类型")
    @ExcelProperty(value = "子收支类型", index = 1)
    @ColumnWidth(15)
    private String sonCategory;

}
