package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;

import java.text.ParseException;

/**
 * <p>
 * 收支记录表 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface IERecordService extends IService<IERecord> {

    /**
     * 添加收支记录
     * @param ieRecord
     * @return
     */
    RetResult addRecord(IERecord ieRecord);

    /**
     * 删除收支记录
     * @param id
     * @return
     */
    RetResult deleteRecord(Integer id);

    /**
     * 修改收支记录
     * @param ieRecord
     * @return
     */
    RetResult updateRecord(IERecord ieRecord);

    /**
     * 查询所有收支记录
     * @param
     * @return
     */
    RetResult selectAllRecord();

    /**
     * 分页模糊查询当前用户收支记录
     * @param ieRecordVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageRecord(IERecordVo ieRecordVo, Integer currentPage, Integer pageSize) throws ParseException;

    /**
     * 批量删除收支记录
     * @param ids
     * @return
     */
    RetResult deleteBatchRecord(Integer[] ids);

    /**
     * 根据年份统计每月收支
     * @param year
     * @return
     */
    RetResult statisticByYear(String year);

    /**
     * 根据年-月份统计收支各子类
     * @param time
     * @return
     */
    RetResult statisticSonCategory(String time);

    /**
     * 统计最近10年的收支
     * @param
     * @return
     */
    RetResult statisticTenYear();

    /**
     * 根据自定义时间段统计收支各子类
     * @param fromTime
     * @param toTime
     * @return
     */
    RetResult statisticByPeriod(String fromTime, String toTime);

    /**
     * 统计当前的收支 今天、本周、本月、本年
     * @return
     */
    RetResult statisticRecent();

    /**
     * 根据年-月来分析收支
     * @return
     */
    RetResult analysisByMonth(String yearMonth) throws ParseException;
}
