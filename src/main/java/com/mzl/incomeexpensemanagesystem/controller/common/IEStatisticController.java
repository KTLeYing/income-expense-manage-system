package com.mzl.incomeexpensemanagesystem.controller.common;

import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.IERecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@RequestMapping("/iEStatistic")
@Api(value = "收支统计模块接口", tags = "收支统计模块接口")
public class IEStatisticController {

    @Autowired
    private IERecordService ieRecordService;

    @GetMapping("/statisticTenYear")
    @ApiOperation(value = "统计最近10年的收支")
    public RetResult statisticTenYear(){
        return ieRecordService.statisticTenYear();
    }

    @GetMapping("/statisticByYear")
    @ApiOperation(value = "根据年份统计每月的收支")
    public RetResult statisticByYear(String year){
        return ieRecordService.statisticByYear(year);
    }

    @GetMapping("/statisticSonCategory")
    @ApiOperation(value = "根据年-月份统计收支各子类")
    public RetResult statisticSonCategory(String time){
        return ieRecordService.statisticSonCategory(time);
    }

    @GetMapping("/statisticByPeriod")
    @ApiOperation(value = "根据自定义时间段统计收支各子类 年-月-日")
    public RetResult statisticByPeriod(String fromTime, String toTime){
        return ieRecordService.statisticByPeriod(fromTime, toTime);
    }

    @GetMapping("/statisticRecent")
    @ApiOperation(value = "统计当前的收支 今天、本周、本月、本年")
    public RetResult statisticRecent(){
        return ieRecordService.statisticRecent();
    }

}
