package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzl.incomeexpensemanagesystem.vo.AnalysisVo;
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;
import com.mzl.incomeexpensemanagesystem.vo.StatisticVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 收支记录表 Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface IERecordMapper extends BaseMapper<IERecord> {

    /**
     * 分页模糊查询当前用户收支记录
     * @param page
     * @return
     */
    IPage<IERecordVo> selectPageRecord(@Param("page") IPage<IERecordVo> page, @Param("ieRecordVo") IERecordVo ieRecordVo);

    /**
     * 按年统计每月的收入
     * @param year
     * @param parentCategory
     * @return
     */
    List<StatisticVo> statisticByYear(String year, String parentCategory, Integer userId);

    /**
     * 根据年-月份统计收支各子类
     * @param time
     * @param parentCategory
     * @return
     */
    List<StatisticVo> statisticSonCategory(String time, String parentCategory, Integer userId);

    /**
     * 统计近10年的收支
     * @param year
     * @param leastYear
     * @param parentCategory
     * @param userId
     * @return
     */
    List<StatisticVo> statisticTenYear(Integer year, Integer leastYear, String parentCategory, Integer userId);

    /**
     * 根据自定义时间段统计收支各子类
     * @param fromTime
     * @param toTime
     * @param parentCategory
     * @param userId
     * @return
     */
    List<StatisticVo> statisticByPeriod(String fromTime, String toTime, String parentCategory, Integer userId);

    /**
     * 统计今天收支
     * @return
     */
    Double statisticToday(String parentCategory, Integer userId);

    /**
     * 统计本周收支
     * @param parentCategory
     * @param userId
     * @return
     */
    Double statisticThisWeek(String parentCategory, Integer userId);

    /**
     * 统计本月收支
     * @param parentCategory
     * @param userId
     * @return
     */
    Double statisticThisMonth(String parentCategory, Integer userId);

    /**
     * 统计本年收支
     * @param parentCategory
     * @param userId
     * @return
     */
    Double statisticThisYear(String parentCategory, Integer userId);

    /**
     * 某个月总收支统计
     * @param yearMonth
     * @return
     */
    Double monthTotal(String yearMonth, Integer userId);

    /**
     * 某个月收支统计
     * @param yearMonth
     * @param parentCategory
     * @param userId
     * @return
     */
    AnalysisVo monthCount(String yearMonth, String parentCategory, Integer userId);

}
