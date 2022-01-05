package com.mzl.incomeexpensemanagesystem.mapper;

import com.mzl.incomeexpensemanagesystem.entity.Budget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;

/**
 * <p>
 * 预算表 Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface BudgetMapper extends BaseMapper<Budget> {

    /**
     * 查询月(年-月)预算
     * @param yearMonth
     * @return
     */
    Budget selectMonthBudget(Integer userId, String yearMonth);

    /**
     * 查询年预算
     * @param userId
     * @param year
     * @return
     */
    Budget selectYearBudget(Integer userId, String year);
}
