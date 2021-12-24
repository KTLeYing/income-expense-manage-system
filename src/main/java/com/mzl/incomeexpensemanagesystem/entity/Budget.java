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
 * 预算表
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("budget")
@ApiModel(value="Budget对象", description="预算表")
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "预算自增id")
        @TableId(value = "budget_id", type = IdType.AUTO)
      private Integer budgetId;

      @ApiModelProperty(value = "用户id")
      private Integer userId;

      @ApiModelProperty(value = "预算数量")
      private Double num;

      @ApiModelProperty(value = "创建时间")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;


}
