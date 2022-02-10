package com.mzl.incomeexpensemanagesystem.vo;

import com.mzl.incomeexpensemanagesystem.entity.Feedback;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   FeedbackVo
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2022/2/6 17:49
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="FeedbackVo对象", description="用户反馈参数类表")
public class FeedbackVo extends Feedback implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String username;

}
