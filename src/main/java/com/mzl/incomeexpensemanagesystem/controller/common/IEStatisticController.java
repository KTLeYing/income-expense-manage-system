package com.mzl.incomeexpensemanagesystem.controller.common;

import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.IERecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   IEStatis
 * @Description: 收支统计
 * @Author: v_ktlema
 * @CreateDate: 2021/12/30 22:31
 * @Version: 1.0
 */
@RestController
@RequestMapping("/iEStatistic")
@Api(value = "收支统计模块接口", tags = "收支统计模块接口")
public class IEStatisticController {

    @Autowired
    private IERecordService ieRecordService;

    @GetMapping("/statisticByYear")
    @ApiOperation(value = "根据年份统计每月收支")
    public RetResult statisticByYear(String year){
        return ieRecordService.statisticByYear(year);
    }

    @GetMapping("/statisticByMonth")
    @ApiOperation(value = "根据年-月份统计各子类收支")
    public RetResult statisticSonCategory(String time){
        return ieRecordService.statisticSonCategory(time);
    }

}
