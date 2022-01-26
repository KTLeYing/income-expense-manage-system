package com.mzl.incomeexpensemanagesystem.controller.manage;


import com.mzl.incomeexpensemanagesystem.annotation.LoginRateLimit;
import com.mzl.incomeexpensemanagesystem.entity.Admin;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.AdminService;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/admin")
@Api(value = "管理员模块接口", tags = "管理员模块接口")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/selectCurrentUser")
    @ApiOperation(value = "获取当前管理员信息")
    public RetResult selectCurrentAdmin(HttpServletRequest request){
        return adminService.selectCurrentAdmin(request);
    }

    @PostMapping("/adminLogin")
    @ApiOperation(value = "管理员登录")
    public RetResult adminLogin(String adminName, String password){
        return adminService.adminLogin(adminName, password);
    }

    @PostMapping("/addAdmin")
    @ApiOperation(value = "添加管理员")
    public RetResult addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改管理员密码")
    public RetResult updatePassword(String oldPassword, String newPassword, String newPassword1){
        return adminService.updatePassword(oldPassword, newPassword, newPassword1);
    }

    @PostMapping("/findBackPassword")
    @ApiOperation(value = "找回管理员密码")
    public RetResult findBackPassword(String newPassword, String newPassword1, String phone, String messageCode){
        return adminService.findBackPassword(newPassword, newPassword1, phone, messageCode);
    }

    @GetMapping("/adminLogout")
    @ApiOperation(value = "管理员退出登录")
    public RetResult adminLogout(HttpServletRequest request){
        return adminService.adminLogout(request);
    }


}

