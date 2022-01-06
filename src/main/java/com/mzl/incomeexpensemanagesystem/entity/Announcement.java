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
 * @since 2022-01-06
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("announcement")
@ApiModel(value="Announcement对象", description="")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "公告自增id")
        @TableId(value = "announcement_id", type = IdType.AUTO)
      private Integer announcementId;

      @ApiModelProperty(value = "公告标题")
      private String title;

      @ApiModelProperty(value = "公告内容")
      private String content;

      @ApiModelProperty(value = "公告发布时间")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;

}
