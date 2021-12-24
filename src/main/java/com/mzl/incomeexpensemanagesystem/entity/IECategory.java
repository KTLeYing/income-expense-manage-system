package com.mzl.incomeexpensemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 收支类型
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("i_e_category")
@ApiModel(value="IECategory对象", description="收支类型")
public class IECategory implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "收支类型自增id")
        @TableId(value = "i_e_category_id", type = IdType.AUTO)
      private Integer iECategoryId;

      @ApiModelProperty(value = "父收支类型")
      private String parentCategory;

      @ApiModelProperty(value = "子收支类型")
      private String sonCategory;


}
