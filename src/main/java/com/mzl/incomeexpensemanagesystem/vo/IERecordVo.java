package com.mzl.incomeexpensemanagesystem.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName :   IERecordVo
 * @Description: User参数实体类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/29 15:11
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="IERecordVo对象", description="收支记录参数类表")
public class IERecordVo extends IERecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父收支类型")
    private String parentCategory;

    @ApiModelProperty(value = "子收支类型")
    private String sonCategory;

    @ApiModelProperty(value = "记录时间 真正上传（年-月-日）")
    private String realCreateTime;

    @ApiModelProperty(value = "记录时间 真正上传(年-月)")
    private String realCreateTimeTwo;

}
