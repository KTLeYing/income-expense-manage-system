package com.mzl.incomeexpensemanagesystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mzl.incomeexpensemanagesystem.entity.Budget;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.mapper.BudgetMapper;
import com.mzl.incomeexpensemanagesystem.mapper.IERecordMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.BudgetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.vo.IEStatisticVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预算表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
@Slf4j
public class BudgetServiceImpl extends ServiceImpl<BudgetMapper, Budget> implements BudgetService {

    @Autowired
    private UserService userService;

    @Autowired
    private IERecordMapper ieRecordMapper;

    @Autowired
    private BudgetMapper budgetMapper;

    /**
     * 设置预算
     * @param budget
     * @return
     */
    @Override
    public RetResult setMonthBudget(Budget budget) {
        Date now = new Date();
        budget.setCreateTime(now);
        budget.setType(1);
        budget.setUserId(userService.getUserId());
        budgetMapper.insert(budget);
        return RetResult.success();
    }

    /**
     * 修改月预算
     * @param budget
     * @return
     */
    @Override
    public RetResult updateMonthBudget(Budget budget) {
        budget.setUserId(userService.getUserId());
        budgetMapper.updateById(budget);
        return RetResult.success();
    }

    /**
     * 查询月(年-月)预算
     * @param yearMonth
     * @return
     */
    @Override
    public RetResult selectMonthBudget(String yearMonth) {
        if (StringUtils.isEmpty(yearMonth)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            yearMonth = sf.format(now);
        }
        Integer userId = userService.getUserId();
        //查询基本支出预算
        Budget budget = budgetMapper.selectMonthBudget(userId, yearMonth);
        if (budget == null){
            return RetResult.success(RetCodeEnum.DATA_EMPTY);
        }
        //选定的月的已支出(消费)
        String parentCategory = "支出";
        Double monthExpense = ieRecordMapper.monthCount(yearMonth, parentCategory, userId).getNum();
        monthExpense = monthExpense == null? 0.00 : monthExpense;
        //选定的月的支出预算的剩余数
        Double monthBudgetRest = budget.getNum() - monthExpense;
        //选定的月的已支出(消费)占比
        Double monthExpensePercent = (double) Math.round((monthExpense / budget.getNum()) * 100) / 100;
        monthExpensePercent = monthExpensePercent >= 1? 1 : monthExpensePercent;
        //选定的月的支出预算的剩余数（占比）
        Double monthBudgetRetPercent = (double) Math.round((1 - monthExpensePercent) * 100) / 100;

        //封装结果
        JSONObject budgetData = new JSONObject();
        budgetData.put("budget", budget);
        budgetData.put("monthExpense", monthExpense);
        budgetData.put("monthExpensePercent", monthExpensePercent);
        budgetData.put("monthBudgetRest", monthBudgetRest);
        budgetData.put("monthBudgetRetPercent", monthBudgetRetPercent);
        return RetResult.success(budgetData);
    }

    /**
     * 查询月(年-月)月度账单
     * @param yearMonth
     * @return
     */
    @Override
    public RetResult selectMonthBill(String yearMonth) {
        if (StringUtils.isEmpty(yearMonth)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            yearMonth = sf.format(now);
        }
        Integer userId = userService.getUserId();
        String parentCategory = "收入";
        Double monthIncome = ieRecordMapper.monthCount(yearMonth, parentCategory, userId).getNum();
        monthIncome = monthIncome == null? 0.00 : monthIncome;
        parentCategory = "支出";
        Double monthExpense = ieRecordMapper.monthCount(yearMonth, parentCategory, userId).getNum();
        monthExpense = monthExpense == null? 0.00 : monthExpense;
        Double monthRest = monthIncome - monthExpense;
        //封装结果
        JSONObject billData = new JSONObject();
        billData.put("monthIncome", monthIncome);
        billData.put("monthExpense", monthExpense);
        billData.put("monthRest", monthRest);
        return RetResult.success(billData);
    }

    /**
     * 查询月(年-月)收支排行Top10
     * @param yearMonth
     * @return
     */
    @Override
    public RetResult selectMonthIERank(String yearMonth) {
        if (StringUtils.isEmpty(yearMonth)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            yearMonth = sf.format(now);
        }
        Integer userId = userService.getUserId();
        //查询收入排行榜Top10
        String parentCategory = "收入";
        List<IEStatisticVo> monthIncomeRank = ieRecordMapper.monthRank(parentCategory, userId, yearMonth);
        //查询支出排行榜Top10
        parentCategory = "支出";
        List<IEStatisticVo> monthExpenseRank = ieRecordMapper.monthRank(parentCategory, userId, yearMonth);
        //封装结果
        JSONObject rankData = new JSONObject();
        rankData.put("monthIncome", monthIncomeRank);
        rankData.put("monthExpense", monthExpenseRank);
        return RetResult.success(rankData);
    }

    /**
     * 删除月(年-月)预算
     * @param id
     * @return
     */
    @Override
    public RetResult deleteMonthBudget(Integer id) {
        budgetMapper.deleteById(id);
        return RetResult.success();
    }

    /**
     * 设置年预算
     * @param budget
     * @return
     */
    @Override
    public RetResult setYearBudget(Budget budget) {
        Date now = new Date();
        budget.setCreateTime(now);
        budget.setType(2);
        budget.setUserId(userService.getUserId());
        budgetMapper.insert(budget);
        return RetResult.success();
    }

    /**
     * 修改年预算
     * @param budget
     * @return
     */
    @Override
    public RetResult updateYearBudget(Budget budget) {
        budget.setUserId(userService.getUserId());
        budgetMapper.updateById(budget);
        return RetResult.success();
    }

    /**
     * 删除年预算
     * @param id
     * @return
     */
    @Override
    public RetResult deleteYearBudget(Integer id) {
        budgetMapper.deleteById(id);
        return RetResult.success();
    }

    /**
     * 查询年预算
     * @param year
     * @return
     */
    @Override
    public RetResult selectYearBudget(String year) {
        if (StringUtils.isEmpty(year)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy");
            year = sf.format(now);
        }
        Integer userId = userService.getUserId();
        //基本的支出预算
        Budget budget = budgetMapper.selectYearBudget(userId, year);
        if (budget == null || budget.getNum() == 0){
            return RetResult.success(RetCodeEnum.DATA_EMPTY);
        }
        //选定的年的已支出(消费)
        String parentCategory = "支出";
        Double yearExpense = ieRecordMapper.yearCount(year, parentCategory, userId).getNum();
        yearExpense = yearExpense == null? 0.00 : yearExpense;
        //选定的年的支出预算的剩余数
        Double yearBudgetRest = budget.getNum() - yearExpense;
        //选定的年的已支出(消费)占比
        Double yearExpensePercent = (double) Math.round((yearExpense / budget.getNum()) * 100) / 100;
        yearExpensePercent = yearExpensePercent >= 1? 1 : yearExpensePercent;
        //选定的月的支出预算的剩余数（占比）
        Double yearBudgetRetPercent = (double) Math.round((1 - yearExpensePercent) * 100) / 100;

        //封装结果
        JSONObject budgetData = new JSONObject();
        budgetData.put("budget", budget);
        budgetData.put("yearExpense", yearExpense);
        budgetData.put("yearExpensePercent", yearExpensePercent);
        budgetData.put("yearBudgetRest", yearBudgetRest);
        budgetData.put("yearBudgetRetPercent", yearBudgetRetPercent);
        return RetResult.success(budgetData);
    }

    /**
     * 查询年度账单
     * @param year
     * @return
     */
    @Override
    public RetResult selectYearBill(String year) {
        if (StringUtils.isEmpty(year)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy");
            year = sf.format(now);
        }
        Integer userId = userService.getUserId();
        String parentCategory = "收入";
        Double yearIncome = ieRecordMapper.yearCount(year, parentCategory, userId).getNum();
        yearIncome = yearIncome == null? 0.00 : yearIncome;
        parentCategory = "支出";
        Double yearExpense = ieRecordMapper.yearCount(year, parentCategory, userId).getNum();
        yearExpense = yearExpense == null? 0.00 : yearExpense;
        Double yearRest = yearIncome - yearExpense;
        //封装结果
        JSONObject billData = new JSONObject();
        billData.put("yearIncome", yearIncome);
        billData.put("yearExpense", yearExpense);
        billData.put("yearRest", yearRest);
        return RetResult.success(billData);
    }

    /**
     * 查询年收支排行Top10
     * @param year
     * @return
     */
    @Override
    public RetResult selectYearIERank(String year) {
        if (StringUtils.isEmpty(year)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy");
            year = sf.format(now);
        }
        Integer userId = userService.getUserId();
        //查询收入排行榜Top10
        String parentCategory = "收入";
        List<IEStatisticVo> yearIncomeRank = ieRecordMapper.yearRank(parentCategory, userId, year);
        //查询支出排行榜Top10
        parentCategory = "支出";
        List<IEStatisticVo> yearExpenseRank = ieRecordMapper.yearRank(parentCategory, userId, year);
        //封装结果
        JSONObject rankData = new JSONObject();
        rankData.put("yearIncome", yearIncomeRank);
        rankData.put("yearExpense", yearExpenseRank);
        return RetResult.success(rankData);
    }


}
