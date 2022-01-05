package com.mzl.incomeexpensemanagesystem.vo;

import com.mzl.incomeexpensemanagesystem.entity.WishList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   WishListVo
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2022/1/5 13:39
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WishListVo对象", description="心愿单返回实体类表")
public class WishListVo extends WishList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录时间 真正上传(年-月-日)")
    private String realCreateTime;

}
