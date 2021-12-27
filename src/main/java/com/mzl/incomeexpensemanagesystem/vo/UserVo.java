package com.mzl.incomeexpensemanagesystem.vo;

import com.mzl.incomeexpensemanagesystem.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @ClassName :   UserVo
 * @Description: User参数实体类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/27 16:25
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UserVo对象", description="用户参数类表")
public class UserVo extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮箱验证码")
    private String emailCode;

}
