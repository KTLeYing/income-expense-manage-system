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
 * 心愿单表
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("wish_list")
@ApiModel(value="WishList对象", description="心愿单表")
public class WishList implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "许愿单自增id")
        @TableId(value = "wish_list_id", type = IdType.AUTO)
      private Integer wishListId;

      @ApiModelProperty(value = "用户id")
      private Integer userId;

      @ApiModelProperty(value = "心愿单名")
      private String name;

      @ApiModelProperty(value = "心愿单内容")
      private String content;

      @ApiModelProperty(value = "心愿的钱数")
      private Double num;

      @ApiModelProperty(value = "状态，0：未完成  1: 完成")
      private Boolean state;

      @ApiModelProperty(value = "创建时间")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;

}
