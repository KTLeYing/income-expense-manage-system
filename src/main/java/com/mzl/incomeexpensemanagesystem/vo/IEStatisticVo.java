package com.mzl.incomeexpensemanagesystem.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   StatisticVo
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2021/12/30 22:41
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="StatisticVo对象", description="收支统计返回实体类表")
public class IEStatisticVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "钱数")
    private Double num;

    @ApiModelProperty(value = "子类名称")
    private String sonCategory;

    @ApiModelProperty(value = "子类占比")
    private Double sonCategoryPercent;

}
