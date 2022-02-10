package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzl.incomeexpensemanagesystem.excel.vo.UserExcelVo;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.UserStatisticVo;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     * 导出所有用户信息Excel(管理员)
     * @param userExcelVoPage
     * @return
     */
    Page<UserExcelVo> selectPageUserExcel(Page<UserExcelVo> userExcelVoPage);

    /**
     * 近10年用户激活数对比(管理员)
     * @param fromYear
     * @param toYear
     * @return
     */
    List<UserStatisticVo> tenYearUserActive(String fromYear, String toYear);

    /**
     * 反馈活跃的前10用户(管理员)
     * @return
     */
    List<UserStatisticVo> tenFeedbackUser();

    /**
     * 禁用用户(管理员)
     * @param userId
     * @return
     */
    void banUser(Integer userId);

    /**
     * 批量禁用用户(管理员)
     * @param userIdsList
     */
    void banBatchUser(List<Integer> userIdsList);

    /**
     * 解禁用户(管理员)
     * @param userId
     */
    void unBanUser(Integer userId);

    /**
     * 批量解禁用户(管理员)
     * @param userIdsList
     */
    void unBanBatchUser(List<Integer> userIdsList);
}
