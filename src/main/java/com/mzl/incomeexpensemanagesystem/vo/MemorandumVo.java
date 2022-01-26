package com.mzl.incomeexpensemanagesystem.vo;

import com.mzl.incomeexpensemanagesystem.entity.Memorandum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   MemorandumVo
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2022/1/5 16:01
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MemorandumVo对象", description="备忘录返回实体类表")
public class MemorandumVo extends Memorandum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录时间 真正上传(年-月-日)")
    private String realCreateTime;

    @ApiModelProperty(value = "用户名")
    private String username;

}
