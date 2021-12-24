package com.mzl.incomeexpensemanagesystem.controller.common;

import com.mzl.incomeexpensemanagesystem.annotation.LoginRateLimit;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: v_ktlema
 * @CreateDate: 2021/12/20 21:41
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户模块接口", tags = "用户模块接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/userLogin")
    @ApiOperation(value = "用户登录")
    @LoginRateLimit(limitNum = 2)  //开启限流，每秒允许请求2次
    public RetResult userLogin(String username, String password, String verifyCode){
        return userService.userLogin(username, password, verifyCode);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public RetResult register(User user){
        return userService.register(user);
    }

    @PostMapping("/userLogout")
    @ApiOperation(value = "用户退出登录")
    public RetResult userLogout(HttpServletRequest request){
        return userService.userLogout(request);
    }

}
