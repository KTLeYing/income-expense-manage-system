package com.mzl.incomeexpensemanagesystem.controller.common;


import com.mzl.incomeexpensemanagesystem.entity.Budget;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.BudgetService;
import com.mzl.incomeexpensemanagesystem.service.IERecordService;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 预算表 前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/budget")
@Api(value = "支出预算模块接口", tags = "支出预算模块接口")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    IERecordService ieRecordService;

    @Autowired
    private UserService userService;

    @PostMapping("/setMonthBudget")
    @ApiOperation(value = "设置月(年-月)预算")
    public RetResult setMonthBudget(@RequestBody Budget budget){
        return budgetService.setMonthBudget(budget);
    }

    @PostMapping("/updateMonthBudget")
    @ApiOperation(value = "修改月(年-月)预算")
    public RetResult updateMonthBudget(@RequestBody Budget budget){
        return budgetService.updateMonthBudget(budget);
    }

    @GetMapping("/deleteMonthBudget")
    @ApiOperation(value = "删除月(年-月)预算")
    public RetResult deleteMonthBudget(Integer id){
        return budgetService.deleteMonthBudget(id);
    }

    @GetMapping("/selectMonthBudget")
    @ApiOperation(value = "查询月(年-月)预算")
    public RetResult selectMonthBudget(String yearMonth){
        return budgetService.selectMonthBudget(yearMonth);
    }

    @GetMapping("/selectMonthBill")
    @ApiOperation(value = "查询月(年-月)月度账单")
    public RetResult selectMonthBill(String yearMonth){
        return budgetService.selectMonthBill(yearMonth);
    }

    @GetMapping("/selectMonthIERank")
    @ApiOperation(value = "查询月(年-月)收支排行Top10")
    public RetResult selectMonthIERank(String yearMonth){
        return budgetService.selectMonthIERank(yearMonth);
    }

    @PostMapping("/setYearBudget")
    @ApiOperation(value = "设置年预算")
    public RetResult setYearBudget(@RequestBody Budget budget){
        return budgetService.setYearBudget(budget);
    }

    @PostMapping("/updateYearBudget")
    @ApiOperation(value = "修改年预算")
    public RetResult updateYearBudget(@RequestBody Budget budget){
        return budgetService.updateYearBudget(budget);
    }

    @GetMapping("/deleteYearBudget")
    @ApiOperation(value = "删除年预算")
    public RetResult deleteYearBudget(Integer id){
        return budgetService.deleteYearBudget(id);
    }

    @GetMapping("/updateYearBudget")
    @ApiOperation(value = "查询年预算")
    public RetResult selectYearBudget(String year){
        return budgetService.selectYearBudget(year);
    }

    @GetMapping("/selectYearBill")
    @ApiOperation(value = "查询年度账单")
    public RetResult selectYearBill(String year){
        return budgetService.selectYearBill(year);
    }

    @GetMapping("/selectYearIERank")
    @ApiOperation(value = "查询年收支排行Top10")
    public RetResult selectYearIERank(String year){
        return budgetService.selectYearIERank(year);
    }

}

