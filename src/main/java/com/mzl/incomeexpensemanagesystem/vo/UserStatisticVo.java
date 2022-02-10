package com.mzl.incomeexpensemanagesystem.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   UserStatisticVo
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2022/2/9 15:16
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UserStatisticVo对象", description="用户统计返回实体类表")
public class UserStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "激活数")
    private Integer activeNum;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "反馈数")
    private String postNum;

}
