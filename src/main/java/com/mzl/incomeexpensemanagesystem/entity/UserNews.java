package com.mzl.incomeexpensemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author v_ktlema
 * @since 2022-01-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("user_news")
@ApiModel(value="UserNews对象", description="")
public class UserNews implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "新闻收藏表自增id")
        @TableId(value = "user_news_id", type = IdType.AUTO)
      private Integer userNewsId;

      @ApiModelProperty(value = "用户id")
      private Integer userId;

      @ApiModelProperty(value = "新闻id")
      private Integer newsId;

      @ApiModelProperty(value = "收藏状态，0：未收藏/取消收藏  1：已收藏")
      private Boolean status;

}
