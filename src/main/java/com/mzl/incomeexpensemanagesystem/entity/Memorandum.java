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
 * 备忘录表
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("memorandum")
@ApiModel(value="Memorandum对象", description="备忘录表")
public class Memorandum implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "备忘录自增id")
        @TableId(value = "memorandum_id", type = IdType.AUTO)
      private Integer memorandumId;

      @ApiModelProperty(value = "用户id")
      private Integer userId;

      @ApiModelProperty(value = "文件保存路径")
      private String savePath;

      @ApiModelProperty(value = "内容")
      private String content;

      @ApiModelProperty(value = "备忘录标题")
      private String title;

      @ApiModelProperty(value = "记录时间/创建时间")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;


}
