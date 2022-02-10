package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzl.incomeexpensemanagesystem.excel.vo.IERecordExcelADVo;
import com.mzl.incomeexpensemanagesystem.vo.AnalysisVo;
import com.mzl.incomeexpensemanagesystem.excel.vo.IERecordExcelVo;
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;
import com.mzl.incomeexpensemanagesystem.vo.IEStatisticVo;
import org.apache.ibatis.annotations.Param;

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
    List<IEStatisticVo> statisticByYear(String year, String parentCategory, Integer userId);

    /**
     * 根据年-月份统计收支各子类
     * @param time
     * @param parentCategory
     * @return
     */
    List<IEStatisticVo> statisticSonCategory(String time, String parentCategory, Integer userId);

    /**
     * 统计近10年的收支
     * @param year
     * @param leastYear
     * @param parentCategory
     * @param userId
     * @return
     */
    List<IEStatisticVo> statisticTenYear(Integer year, Integer leastYear, String parentCategory, Integer userId);

    /**
     * 根据自定义时间段统计收支各子类
     * @param fromTime
     * @param toTime
     * @param parentCategory
     * @param userId
     * @return
     */
    List<IEStatisticVo> statisticByPeriod(String fromTime, String toTime, String parentCategory, Integer userId);

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

    /**
     * 某一年总收支统计
     * @param year
     * @return
     */
    Double yearTotal(String year, Integer userId);

    /**
     * 某一年收支统计
     * @param year
     * @param parentCategory
     * @param userId
     * @return
     */
    AnalysisVo yearCount(String year, String parentCategory, Integer userId);

    /**
     * 查询月(年-月)收支排行榜Top10
     * @param parentCategory
     * @param userId
     * @param yearMonth
     * @return
     */
    List<IEStatisticVo> monthRank(String parentCategory, Integer userId, String yearMonth);

    /**
     * 查询年收支排行榜Top10
     * @param parentCategory
     * @param userId
     * @param year
     * @return
     */
    List<IEStatisticVo> yearRank(String parentCategory, Integer userId, String year);

    /**
     * 分页模糊查询收支记录(管理员)
     * @param page
     * @param ieRecordVo
     * @return
     */
    IPage<IERecordVo> selectPageRecordAD(IPage<IERecordVo> page, IERecordVo ieRecordVo);

    /**
     * 导出收支记录的分页查询
     * @param page
     * @return
     */
    Page<IERecordExcelVo> selectPageRecordExcel(Page<IERecordExcelVo> page, Integer userId);

    /**
     * 导出所有用户收支记录Excel(管理员)
     * @param ieRecordExcelADVoPage
     * @return
     */
    Page<IERecordExcelADVo> selectPageRecordExcelAD(Page<IERecordExcelADVo> ieRecordExcelADVoPage);

}
