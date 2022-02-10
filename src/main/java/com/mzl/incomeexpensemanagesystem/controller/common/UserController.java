package com.mzl.incomeexpensemanagesystem.controller.common;

import com.mzl.incomeexpensemanagesystem.annotation.LoginRateLimit;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

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
    public RetResult register(@RequestBody UserVo userVo){
        return userService.register(userVo);
    }

    @GetMapping("/userLogout")
    @ApiOperation(value = "用户退出登录")
    public RetResult userLogout(HttpServletRequest request){
        return userService.userLogout(request);
    }

    @GetMapping("/selectCurrentUser")
    @ApiOperation(value = "获取当前用户信息")
    public RetResult selectCurrentUser(HttpServletRequest request){
        return userService.selectCurrentUser(request);
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改用户密码")
    public RetResult updatePassword(String oldPassword, String newPassword, String newPassword1){
        return userService.updatePassword(oldPassword, newPassword, newPassword1);
    }

    @PostMapping("/findBackPassword")
    @ApiOperation(value = "找回密码")
    public RetResult findBackPassword(String newPassword, String newPassword1, String userName, String messageCode){
        return userService.findBackPassword(newPassword, newPassword1, userName, messageCode);
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "修改用户信息")
    public RetResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/deleteUser")
    @ApiOperation(value = "注销(删除)用户")
    public RetResult deleteUser(Integer userId){
        return userService.deleteUser(userId);
    }

}
