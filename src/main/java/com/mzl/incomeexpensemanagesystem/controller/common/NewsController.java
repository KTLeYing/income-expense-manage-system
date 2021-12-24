package com.mzl.incomeexpensemanagesystem.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   NewsController
 * @Description: 新闻控制器
 * @Author: v_ktlema
 * @CreateDate: 2021/12/20 22:09
 * @Version: 1.0
 */
@RestController
@RequestMapping("/news")
@Api(value = "新闻模块接口", tags = "新闻模块接口")
public class NewsController {

    @GetMapping("/test")
    @ApiOperation(value = "测试接口")
    public String test(){
        return "加油！";
    }

}
