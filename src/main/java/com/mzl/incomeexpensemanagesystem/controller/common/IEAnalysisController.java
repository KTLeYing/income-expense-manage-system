package com.mzl.incomeexpensemanagesystem.controller.common;

import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.IERecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @ClassName :   IEAnalysis
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2021/12/31 17:02
 * @Version: 1.0
 */
@RestController
@RequestMapping("/iEStatistic")
@Api(value = "收支分析模块接口", tags = "收支分析模块接口")
public class IEAnalysisController {

    @Autowired
    private IERecordService ieRecordService;

    @GetMapping("/analysisByMonth")
    @ApiOperation(value = "根据年-月来分析收支")
    public RetResult analysisByMonth(String yearMonth) throws ParseException {
        return ieRecordService.analysisByMonth(yearMonth);
    }


}
