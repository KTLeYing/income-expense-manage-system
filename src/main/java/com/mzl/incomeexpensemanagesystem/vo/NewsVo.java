package com.mzl.incomeexpensemanagesystem.vo;

import com.mzl.incomeexpensemanagesystem.entity.News;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   NewsVo
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2022/1/6 10:58
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NewsVo对象", description="新闻参数类表")
public class NewsVo extends News implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}
