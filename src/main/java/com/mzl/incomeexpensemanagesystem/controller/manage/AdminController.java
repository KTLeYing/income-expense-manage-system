package com.mzl.incomeexpensemanagesystem.controller.manage;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    @ApiOperation(value = "测试接口")
    public String test(){
        return "加油！";
    }

}

