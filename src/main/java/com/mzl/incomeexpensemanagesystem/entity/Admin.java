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
 * 管理员表
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("admin")
@ApiModel(value="Admin对象", description="管理员表")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "管理员自增id")
      @TableId(value = "admin_id", type = IdType.AUTO)
      private Integer adminId;

      @ApiModelProperty(value = "管理员登录名")
      private String adminName;

      @ApiModelProperty(value = "密码")
      private String password;

      @ApiModelProperty(value = "手机号")
      private String phone;

      @ApiModelProperty(value = "头像路径")
      private String avatarPath;


}
