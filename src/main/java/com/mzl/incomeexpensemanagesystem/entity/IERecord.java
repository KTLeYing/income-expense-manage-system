package com.mzl.incomeexpensemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 收支记录表
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("i_e_record")
@ApiModel(value="IERecord对象", description="收支记录表")
public class IERecord implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "收支记录自增id")
        @TableId(value = "i_e_record_id", type = IdType.AUTO)
      private Integer iERecordId;

      @ApiModelProperty(value = "用户id")
      private Integer userId;

      @ApiModelProperty(value = "收支类型id")
      private Integer iCCategoryId;

      @ApiModelProperty(value = "收支数量(钱数)")
      private Integer num;

      @ApiModelProperty(value = "收支备注")
      private String note;

      @ApiModelProperty(value = "记录时间")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;


}
