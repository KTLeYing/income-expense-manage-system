package com.mzl.incomeexpensemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 
 * </p>
 *
 * @author v_ktlema
 * @since 2022-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("feedback")
@ApiModel(value="Feedback对象", description="用户反馈")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "用户反馈自增id")
      @TableId(value = "feedback_id", type = IdType.AUTO)
      private Integer feedbackId;

      @ApiModelProperty(value = "用户id")
      private Integer userId;

      @ApiModelProperty(value = "反馈内容")
      private String content;

      @ApiModelProperty(value = "收藏反馈: 1:未收藏  2:已收藏")
      private Integer collected;

      @ApiModelProperty(value = "反馈创建时间")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;


}
