package com.mzl.incomeexpensemanagesystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.mzl.incomeexpensemanagesystem.mapper.IERecordMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.IERecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.vo.AnalysisVo;
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;
import com.mzl.incomeexpensemanagesystem.vo.StatisticVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 收支记录表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
@Transactional
@Slf4j
public class IERecordServiceImpl extends ServiceImpl<IERecordMapper, IERecord> implements IERecordService {

    @Autowired
    private IERecordMapper ieRecordMapper;

    @Autowired
    private UserService  userService;

    /**
     * 添加收支记录
     * @param ieRecord
     * @return
     */
    @Override
    public RetResult addRecord(IERecord ieRecord) {
        Date now = new Date();
        ieRecord.setCreateTime(now);
        ieRecord.setUserId(userService.getUser().getUserId());
        ieRecordMapper.insert(ieRecord);
        return RetResult.success();
    }

    /**
     * 删除收支记录
     * @param id
     * @return
     */
    @Override
    public RetResult deleteRecord(Integer id) {
        ieRecordMapper.deleteById(id);
        return RetResult.success();
    }

    /**
     * 修改收支记录
     * @param ieRecord
     * @return
     */
    @Override
    public RetResult updateRecord(IERecord ieRecord) {
        ieRecord.setUserId(userService.getUser().getUserId());
        ieRecordMapper.updateById(ieRecord);
        return RetResult.success();
    }

    /**
     * 查询当前用户所有收支记录
     * @param
     * @return
     */
    @Override
    public RetResult selectAllRecord() {
        QueryWrapper<IERecord> queryWrapper = new QueryWrapper<>();
        //获取当前用户
        Integer userId = userService.getUser().getUserId();
        queryWrapper.eq("user_id", userId);
        List<IERecord> recordList = ieRecordMapper.selectList(queryWrapper);
        return RetResult.success(recordList);
    }

    /**
     * 分页模糊查询当前用户收支记录
     * @param ieRecordVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageRecord(IERecordVo ieRecordVo, Integer currentPage, Integer pageSize) throws ParseException {
        if (currentPage == null || currentPage == 0){
            //不传默认为第一页
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            //不传默认10条
            pageSize = 10;
        }
        //获取当前用户
        Integer userId = userService.getUser().getUserId();
        ieRecordVo.setUserId(userId);
        log.info(ieRecordVo.toString());
        IPage<IERecordVo> page = new Page<>(currentPage, pageSize);
        IPage<IERecordVo> ieCategoryIPage = ieRecordMapper.selectPageRecord(page, ieRecordVo);
        log.info("收支类型分页结果：" + ieCategoryIPage.getRecords());
        return RetResult.success(ieCategoryIPage);
    }

    /**
     * 批量删除收支记录
     * @param ids
     * @return
     */
    @Override
    public RetResult deleteBatchRecord(Integer[] ids) {
        List<Integer> idsList = Arrays.stream(ids).collect(Collectors.toList());
        ieRecordMapper.deleteBatchIds(idsList);
        return RetResult.success();
    }

    /**
     * 统计最近10年的收支
     * @return
     */
    @Override
    public RetResult statisticTenYear() {
        Integer userId = userService.getUserId();
        //获取当前的年
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer leastYear = year - 10;
        //统计近10年收入
        String parentCategory = "收入";
        List<StatisticVo> statisticIncomeVos = ieRecordMapper.statisticTenYear(year, leastYear, parentCategory, userId);
        //统计近10年支出
        parentCategory = "支出";
        List<StatisticVo> statisticExpenseVos = ieRecordMapper.statisticTenYear(year, leastYear, parentCategory, userId);
        HashMap<String, Object> statisticsData = new HashMap<>();
        statisticsData.put("statisticIncomeVos", statisticIncomeVos);
        statisticsData.put("statisticExpenseVos", statisticExpenseVos);
        return RetResult.success(statisticsData);
    }

    /**
     * 根据年份统计每月收支
     * @param year
     * @return
     */
    @Override
    public RetResult statisticByYear(String year) {
        if (StringUtils.isEmpty(year)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy");
            year = sf.format(now);
        }
        Integer userId = userService.getUser().getUserId();
        //统计该年每月的收支消费数
        //统计每月收入
        String parentCategory = "收入";
        List<StatisticVo> statisticIncomeVos = ieRecordMapper.statisticByYear(year, parentCategory, userId);
        //统计每月支出
        parentCategory = "支出";
        List<StatisticVo> statisticExpenseVos = ieRecordMapper.statisticByYear(year, parentCategory, userId);
        HashMap<String, Object> statisticsData = new HashMap<>();
        statisticsData.put("statisticIncomeVos", statisticIncomeVos);
        statisticsData.put("statisticExpenseVos", statisticExpenseVos);
        return RetResult.success(statisticsData);
    }

    /**
     * 根据年-月份统计收支各子类
     * @param time
     * @return
     */
    @Override
    public RetResult statisticSonCategory(String time) {
        if (StringUtils.isEmpty(time)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            time = sf.format(now);
        }
        Integer userId = userService.getUser().getUserId();
        String parentCategory = "收入";
        //统计收入的个子类占比
        List<StatisticVo> statisticIncomeVos = ieRecordMapper.statisticSonCategory(time, parentCategory, userId);
        //统计支出的个子类占比
        parentCategory = "支出";
        List<StatisticVo> statisticExpenseVos = ieRecordMapper.statisticSonCategory(time, parentCategory, userId);
        HashMap<String, Object> statisticsData = new HashMap<>();
        statisticsData.put("statisticIncomeVos", statisticIncomeVos);
        statisticsData.put("statisticExpenseVos", statisticExpenseVos);
        return RetResult.success(statisticsData);
    }

    /**
     * 根据自定义时间段统计收支各子类
     * @param fromTime
     * @param toTime
     * @return
     */
    @Override
    public RetResult statisticByPeriod(String fromTime, String toTime) {
        if (StringUtils.isEmpty(fromTime)){
            //当前日期减一个月
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //创建日历对象
            Calendar cal = Calendar.getInstance();
            //将时间日期数据传入日历对象
            cal.setTime(now);
            //设置月份加1
            cal.add(Calendar.MONTH, -1);
            //获取到减1的月份
            Date now1 = cal.getTime();
            fromTime = sf.format(now1);
        }
        if (StringUtils.isEmpty(toTime)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            toTime = sf.format(now);
        }
        Integer userId = userService.getUser().getUserId();
        String parentCategory = "收入";
        //统计收入的各子类
        List<StatisticVo> statisticIncomeVos = ieRecordMapper.statisticByPeriod(fromTime, toTime, parentCategory, userId);
        //统计支出的各子类
        parentCategory = "支出";
        List<StatisticVo> statisticExpenseVos = ieRecordMapper.statisticByPeriod(fromTime, toTime, parentCategory, userId);
        HashMap<String, Object> statisticsData = new HashMap<>();
        statisticsData.put("statisticIncomeVos", statisticIncomeVos);
        statisticsData.put("statisticExpenseVos", statisticExpenseVos);
        return RetResult.success(statisticsData);
    }

    /**
     * 统计当前的收支 今天、本周、本月、本年
     * @return
     */
    @Override
    public RetResult statisticRecent() {
        Integer userId = userService.getUserId();
        //收入统计
        String parentCategory = "收入";
        //今日
        Double todayIncome = ieRecordMapper.statisticToday(parentCategory, userId);
        //本周
        Double thisWeekIncome = ieRecordMapper.statisticThisWeek(parentCategory, userId);
        //本月
        Double thisMonthIncome = ieRecordMapper.statisticThisMonth(parentCategory, userId);
        //本年
        Double thisYearIncome = ieRecordMapper.statisticThisYear(parentCategory, userId);

        //支出统计
        parentCategory = "支出";
        //今日
        Double todayExpense = ieRecordMapper.statisticToday(parentCategory, userId);
        //本周
        Double thisWeekExpense = ieRecordMapper.statisticThisWeek(parentCategory, userId);
        //本月
        Double thisMonthExpense = ieRecordMapper.statisticThisMonth(parentCategory, userId);
        //本年
        Double thisYearExpense = ieRecordMapper.statisticThisYear(parentCategory, userId);

        //收入统计结果封装
        HashMap<String, Object> statisticIncome = new HashMap<>();
        statisticIncome.put("todayIncome", todayIncome);
        statisticIncome.put("thisWeekIncome", thisWeekIncome);
        statisticIncome.put("thisMonthIncome", thisMonthIncome);
        statisticIncome.put("thisYearIncome", thisYearIncome);
        //支出统计结果封装
        HashMap<String, Object> statisticExpense = new HashMap<>();
        statisticExpense.put("todayExpense", todayExpense);
        statisticExpense.put("thisWeekExpense", thisWeekExpense);
        statisticExpense.put("thisMonthExpense", thisMonthExpense);
        statisticExpense.put("thisYearExpense", thisYearExpense);
        //总结果
        JSONObject statisticData = new JSONObject();
        statisticData.put("statisticIncome", statisticIncome);
        statisticData.put("statisticExpense", statisticExpense);
        return RetResult.success(statisticData);
    }

    /**
     * 根据年-月来分析收支
     * @return
     */
    @Override
    public RetResult analysisByMonth(String yearMonth) throws ParseException {
        if (StringUtils.isEmpty(yearMonth)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            yearMonth = sf.format(now);
        }
        Integer userId = userService.getUserId();
        //本月收支情况
        //本月总收支
        Double thisMonthTotal = ieRecordMapper.monthTotal(yearMonth, userId);
        //本月收入+占比+条数
        String parentCategory = "收入";
        AnalysisVo thisMonthIncomeVo = ieRecordMapper.monthCount(yearMonth, parentCategory, userId);
        Double thisMonthIncome = thisMonthIncomeVo.getNum();
        Integer thisMonthIncomeRecord = thisMonthIncomeVo.getRecordNum();
        //本月支出+占比+条数
        parentCategory = "支出";
        AnalysisVo thisMonthExpenseVo = ieRecordMapper.monthCount(yearMonth, parentCategory, userId);
        Double thisMonthExpense = thisMonthExpenseVo.getNum();
        Integer thisMonthExpenseRecord = thisMonthExpenseVo.getRecordNum();
        //本月净收入(存入银行卡)
        Double thisMonthBalance =  thisMonthIncome - thisMonthExpense;

        //上个月收支情况
        //求上个月日期
        //当前日期减一个月
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date lastMonth = sf.parse(yearMonth);
        //创建日历对象
        Calendar cal = Calendar.getInstance();
        //将时间日期数据传入日历对象
        cal.setTime(lastMonth);
        //设置月份加1
        cal.add(Calendar.MONTH, -1);
        //获取到减1的月份
        Date lastMonthDate = cal.getTime();
        String lastMonth1 = sf.format(lastMonthDate);
        //上个月总收支
        Double lastMonthTotal = ieRecordMapper.monthTotal(lastMonth1, userId);
        //上个月收入+占比+条数
        parentCategory = "收入";
        AnalysisVo lastMonthIncomeVo = ieRecordMapper.monthCount(lastMonth1, parentCategory, userId);
        Double lastMonthIncome = lastMonthIncomeVo.getNum();
        Integer lastMonthIncomeRecord = lastMonthIncomeVo.getRecordNum();
        //上个月支出+占比+条数
        parentCategory = "支出";
        AnalysisVo lastMonthExpenseVo = ieRecordMapper.monthCount(yearMonth, parentCategory, userId);
        Double lastMonthExpense = lastMonthExpenseVo.getNum();
        Integer lastMonthExpenseRecord = lastMonthExpenseVo.getRecordNum();
        //上个月净收入(存入银行卡)
        Double lastMonthBalance =  lastMonthIncome - lastMonthExpense;
        //封装这个月结果
        JSONObject thisMonthData = new JSONObject();

        //封装上个月结果
        JSONObject lastMonthData = new JSONObject();

        //封装这总结果
        JSONObject analysisData = new JSONObject();
        analysisData.put("thisMonthData", thisMonthData);
        analysisData.put("lastMonthData", lastMonthData);

        return RetResult.success(analysisData);
    }

}
