package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.Budget;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;

/**
 * <p>
 * 预算表 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface BudgetService extends IService<Budget> {

    /**
     * 设置预算
     * @param budget
     * @return
     */
    RetResult setMonthBudget(Budget budget);

    /**
     * 修改月预算
     * @param budget
     * @return
     */
    RetResult updateMonthBudget(Budget budget);

    /**
     * 查询改月(年-月)预算
     * @param yearMonth
     * @return
     */
    RetResult selectMonthBudget(String yearMonth);

    /**
     * 查询月(年-月)月度账单
     * @param yearMonth
     * @return
     */
    RetResult selectMonthBill(String yearMonth);

    /**
     * 查询月(年-月)收支排行Top10
     * @param yearMonth
     * @return
     */
    RetResult selectMonthIERank(String yearMonth);

    /**
     * 删除月(年-月)预算
     * @param id
     * @return
     */
    RetResult deleteMonthBudget(Integer id);

    /**
     * 设置年预算
     * @param budget
     * @return
     */
    RetResult setYearBudget(Budget budget);

    /**
     * 修改年预算
     * @param budget
     * @return
     */
    RetResult updateYearBudget(Budget budget);

    /**
     * 删除年预算
     * @param id
     * @return
     */
    RetResult deleteYearBudget(Integer id);

    /**
     * 查询年预算
     * @param year
     * @return
     */
    RetResult selectYearBudget(String year);

    /**
     * 查询年度账单
     * @param year
     * @return
     */
    RetResult selectYearBill(String year);

    /**
     * 查询年收支排行Top10
     * @param year
     * @return
     */
    RetResult selectYearIERank(String year);

}
