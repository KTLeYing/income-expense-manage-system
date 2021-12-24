package com.mzl.incomeexpensemanagesystem.controller.common;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 预算表 前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/budget")
@Api(value = "预算模块接口", tags = "预算模块接口")
public class BudgetController {

    @GetMapping("/test")
    @ApiOperation(value = "测试接口")
    public String test(){
        return "加油！";
    }

}

