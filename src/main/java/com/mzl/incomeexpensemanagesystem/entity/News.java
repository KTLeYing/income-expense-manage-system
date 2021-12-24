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
 * 新闻表
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("news")
@ApiModel(value="News对象", description="新闻表")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "新闻自增id")
        @TableId(value = "news_id", type = IdType.AUTO)
      private Integer newsId;

      @ApiModelProperty(value = "新闻标题")
      private String title;

      @ApiModelProperty(value = "作者")
      private String author;

      @ApiModelProperty(value = "关键词")
      private String keyword;

      @ApiModelProperty(value = "访问数")
      private Long visitCount;

      @ApiModelProperty(value = "内容")
      private String content;

      @ApiModelProperty(value = "创建时间")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;


}
