package com.mzl.incomeexpensemanagesystem.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   AnalysisVo
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2021/12/31 19:15
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AnalysisVo对象", description="收支分析返回实体类表")
public class AnalysisVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收支记录数")
    private Integer recordNum;

    @ApiModelProperty(value = "钱数")
    private Double num;

}
