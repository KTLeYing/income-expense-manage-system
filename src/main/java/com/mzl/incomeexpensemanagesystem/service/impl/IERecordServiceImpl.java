package com.mzl.incomeexpensemanagesystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
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

import java.math.BigDecimal;
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
        if (ieRecord.getCreateTime() == null || StringUtils.isEmpty(String.valueOf(ieRecord.getCreateTime()))){
            Date now = new Date();
            ieRecord.setCreateTime(now);
        }
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
     * 根据年-月来分析收支(本月 VS 上个月)
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
        //各种情况的初始化
        Double thisMonthTotal = 0.00;
        //本月收入+占比+条数
        Double thisMonthIncome = 0.00;
        Integer thisMonthIncomeRecord = 0;
        //本月支出+占比+条数
        Double thisMonthExpense = 0.00;
        Integer thisMonthExpenseRecord = 0;
        //本月净收入(存入银行卡)
        Double thisMonthBalance =  0.00;
        //本月的收入占比（四舍五入保留两位小数）
        Double thisMonthIncomePercent = 0.00;
        //本月的支出占比（四舍五入保留两位小数）
        Double thisMonthExpensePercent = 0.00;
        //本月总收支
        thisMonthTotal = ieRecordMapper.monthTotal(yearMonth, userId);
        String parentCategory = "收入";
        if (thisMonthTotal != null && thisMonthTotal != 0){
            //本月收入+占比+条数
            AnalysisVo thisMonthIncomeVo = ieRecordMapper.monthCount(yearMonth, parentCategory, userId);
            thisMonthIncome = (thisMonthIncomeVo == null || thisMonthIncomeVo.getNum() == null)? 0.00: thisMonthIncomeVo.getNum();
            thisMonthIncomeRecord = (thisMonthIncomeVo == null || thisMonthIncomeVo.getRecordNum() == null)? 0 : thisMonthIncomeVo.getRecordNum();
            //本月支出+占比+条数
            parentCategory = "支出";
            AnalysisVo thisMonthExpenseVo = ieRecordMapper.monthCount(yearMonth, parentCategory, userId);
            thisMonthExpense = (thisMonthExpenseVo == null || thisMonthExpenseVo.getNum() == null)? 0.00 : thisMonthExpenseVo.getNum();
            thisMonthExpenseRecord = (thisMonthExpenseVo == null || thisMonthExpenseVo.getRecordNum() == null)? 0 : thisMonthExpenseVo.getRecordNum();
            //本月净收入(存入银行卡)
            thisMonthBalance =  thisMonthIncome - thisMonthExpense;
            //本月的收入占比（四舍五入保留两位小数）
            thisMonthIncomePercent = (double) Math.round((thisMonthIncome / thisMonthTotal) * 100) / 100;
            //本月的支出占比（四舍五入保留两位小数）
            thisMonthExpensePercent = (double) Math.round((1 - thisMonthIncomePercent) * 100) / 100;
        }

        //上个月收支情况
        //各种情况的初始化
        Double lastMonthTotal = 0.00;
        //本月收入+占比+条数
        Double lastMonthIncome = 0.00;
        Integer lastMonthIncomeRecord = 0;
        //本月支出+占比+条数
        Double lastMonthExpense = 0.00;
        Integer lastMonthExpenseRecord = 0;
        //本月净收入(存入银行卡)
        Double lastMonthBalance =  0.00;
        //本月的收入占比（四舍五入保留两位小数）
        Double lastMonthIncomePercent = 0.00;
        //本月的支出占比（四舍五入保留两位小数）
        Double lastMonthExpensePercent = 0.00;
        //求上个月日期
        //当前日期减一个月
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
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
        lastMonthTotal = ieRecordMapper.monthTotal(lastMonth1, userId);
        if (lastMonthTotal != null && lastMonthTotal != 0){
            //上个月收入+占比+条数
            parentCategory = "收入";
            AnalysisVo lastMonthIncomeVo = ieRecordMapper.monthCount(lastMonth1, parentCategory, userId);
            lastMonthIncome = (lastMonthIncomeVo == null || lastMonthIncomeVo.getNum() == null)? 0.00 : lastMonthIncomeVo.getNum();
            lastMonthIncomeRecord = (lastMonthIncomeVo == null || lastMonthIncomeVo.getRecordNum() == null)? 0 : lastMonthIncomeVo.getRecordNum();
            //上个月支出+占比+条数
            parentCategory = "支出";
            AnalysisVo lastMonthExpenseVo = ieRecordMapper.monthCount(lastMonth1, parentCategory, userId);
            lastMonthExpense = (lastMonthExpenseVo == null || lastMonthExpenseVo.getNum() == null)? 0.00 : lastMonthExpenseVo.getNum();
            lastMonthExpenseRecord = (lastMonthExpenseVo == null || lastMonthExpenseVo.getRecordNum() == null)? 0 : lastMonthExpenseVo.getRecordNum();
            //上个月净收入(存入银行卡)
            lastMonthBalance =  lastMonthIncome - lastMonthExpense;
            //上个月月的收入占比（四舍五入保留两位小数）
            lastMonthIncomePercent = (double) Math.round((lastMonthIncome / lastMonthTotal) * 100) / 100;
            //本月的支出占比（四舍五入保留两位小数）
            lastMonthExpensePercent = (double) Math.round((1 - lastMonthIncomePercent) * 100) / 100;
        }

        //封装这个月结果
        JSONObject thisMonthData = new JSONObject();
        thisMonthData.put("thisMonthTotal", thisMonthTotal);
        thisMonthData.put("thisMonthIncome", thisMonthIncome);
        thisMonthData.put("thisMonthIncomeRecord", thisMonthIncomeRecord);
        thisMonthData.put("thisMonthExpense", thisMonthExpense);
        thisMonthData.put("thisMonthExpenseRecord", thisMonthExpenseRecord);
        thisMonthData.put("thisMonthBalance", thisMonthBalance);
        thisMonthData.put("thisMonthIncomePercent", thisMonthIncomePercent);
        thisMonthData.put("thisMonthExpensePercent", thisMonthExpensePercent);
        //封装上个月结果
        JSONObject lastMonthData = new JSONObject();
        lastMonthData.put("lastMonthTotal", lastMonthTotal);
        lastMonthData.put("lastMonthIncome", lastMonthIncome);
        lastMonthData.put("lastMonthIncomeRecord", lastMonthIncomeRecord);
        lastMonthData.put("lastMonthExpense", lastMonthExpense);
        lastMonthData.put("lastMonthExpenseRecord", lastMonthExpenseRecord);
        lastMonthData.put("lastMonthBalance", lastMonthBalance);
        lastMonthData.put("lastMonthIncomePercent", lastMonthIncomePercent);
        lastMonthData.put("lastMonthExpensePercent", lastMonthExpensePercent);
        //封装这总结果
        JSONObject analysisData = new JSONObject();
        analysisData.put("thisMonthData", thisMonthData);
        analysisData.put("lastMonthData", lastMonthData);

        return RetResult.success(analysisData);
    }

    /**
     * 据年份来分析收支(本年 VS 上一年)
     * @param year
     * @return
     */
    @Override
    public RetResult analysisByYear(String year) throws ParseException {
        if (StringUtils.isEmpty(year)){
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy");
            year = sf.format(now);
        }
        Integer userId = userService.getUserId();
        //本年收支情况
        //各种情况的初始化
        Double thisYearTotal = 0.00;
        //本月收入+占比+条数
        Double thisYearIncome = 0.00;
        Integer thisYearIncomeRecord = 0;
        //本月支出+占比+条数
        Double thisYearExpense = 0.00;
        Integer thisYearExpenseRecord = 0;
        //本月净收入(存入银行卡)
        Double thisYearBalance =  0.00;
        //本月的收入占比（四舍五入保留两位小数）
        Double thisYearIncomePercent = 0.00;
        //本月的支出占比（四舍五入保留两位小数）
        Double thisYearExpensePercent = 0.00;
        //本年总收支
        thisYearTotal = ieRecordMapper.yearTotal(year, userId);
        //本年收入+占比+条数
        String parentCategory = "收入";
        if (thisYearTotal != null && thisYearTotal != 0){
            AnalysisVo thisYearIncomeVo = ieRecordMapper.yearCount(year, parentCategory, userId);
            thisYearIncome = (thisYearIncomeVo == null || thisYearIncomeVo.getNum() == null)? 0.00 : thisYearIncomeVo.getNum();
            thisYearIncomeRecord = (thisYearIncomeVo == null || thisYearIncomeVo.getRecordNum() == null)? 0 : thisYearIncomeVo.getRecordNum();
            //本年支出+占比+条数
            parentCategory = "支出";
            AnalysisVo thisYearExpenseVo = ieRecordMapper.yearCount(year, parentCategory, userId);
            thisYearExpense = (thisYearExpenseVo == null || thisYearExpenseVo.getNum() == null)? 0.00 : thisYearExpenseVo.getNum();
            thisYearExpenseRecord = (thisYearExpenseVo == null || thisYearExpenseVo.getRecordNum() == null)? 0 : thisYearExpenseVo.getRecordNum();
            //本年净收入(存入银行卡)
            thisYearBalance =  thisYearIncome - thisYearExpense;
            //本年的收入占比（四舍五入保留两位小数）
            thisYearIncomePercent = (double) Math.round((thisYearIncome / thisYearTotal) * 100) / 100;
            //本年的支出占比（四舍五入保留两位小数）
            thisYearExpensePercent = (double) Math.round((1 - thisYearIncomePercent) * 100) / 100;
        }

        //上年收支情况
        //各种情况的初始化
        Double lastYearTotal = 0.00;
        //本月收入+占比+条数
        Double lastYearIncome = 0.00;
        Integer lastYearIncomeRecord = 0;
        //本月支出+占比+条数
        Double lastYearExpense = 0.00;
        Integer lastYearExpenseRecord = 0;
        //本月净收入(存入银行卡)
        Double lastYearBalance =  0.00;
        //本月的收入占比（四舍五入保留两位小数）
        Double lastYearIncomePercent = 0.00;
        //本月的支出占比（四舍五入保留两位小数）
        Double lastYearExpensePercent = 0.00;
        //求上年日期
        //当前日期减一年
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        Date lastYear = sf.parse(year);
        //创建日历对象
        Calendar cal = Calendar.getInstance();
        //将时间日期数据传入日历对象
        cal.setTime(lastYear);
        //设置年份减1
        cal.add(Calendar.YEAR, -1);
        //获取到减1的年份
        Date lastYearDate = cal.getTime();
        String lastYear1 = sf.format(lastYearDate);
        //上年总收支
        lastYearTotal = ieRecordMapper.yearTotal(lastYear1, userId);
        if (lastYearTotal != null && lastYearTotal != 0){
            //上年收入+占比+条数
            parentCategory = "收入";
            AnalysisVo lastYearIncomeVo = ieRecordMapper.yearCount(lastYear1, parentCategory, userId);
            lastYearIncome = (lastYearIncomeVo == null || lastYearIncomeVo.getNum() == null)? 0.00 : lastYearIncomeVo.getNum();
            lastYearIncomeRecord = (lastYearIncomeVo == null || lastYearIncomeVo.getRecordNum() == null)? 0 : lastYearIncomeVo.getRecordNum();
            //上年支出+占比+条数
            parentCategory = "支出";
            AnalysisVo lastYearExpenseVo = ieRecordMapper.yearCount(lastYear1, parentCategory, userId);
            lastYearExpense = (lastYearExpenseVo == null || lastYearExpenseVo.getNum() == null)? 0.00 : lastYearExpenseVo.getNum();
            lastYearExpenseRecord = (lastYearExpenseVo == null || lastYearExpenseVo.getRecordNum() == null)? 0 : lastYearExpenseVo.getRecordNum();
            //上年净收入(存入银行卡)
            lastYearBalance =  lastYearIncome - lastYearExpense;
            //上年的收入占比（四舍五入保留两位小数）
            lastYearIncomePercent = (double) Math.round((lastYearIncome / lastYearTotal) * 100) / 100;
            //本年的支出占比（四舍五入保留两位小数）
            lastYearExpensePercent = (double) Math.round((1 - lastYearIncomePercent) * 100) / 100;
        }

        //封装这个月结果
        JSONObject thisYearData = new JSONObject();
        thisYearData.put("thisYearTotal", thisYearTotal);
        thisYearData.put("thisYearIncome", thisYearIncome);
        thisYearData.put("thisYearIncomeRecord", thisYearIncomeRecord);
        thisYearData.put("thisYearExpense", thisYearExpense);
        thisYearData.put("thisYearExpenseRecord", thisYearExpenseRecord);
        thisYearData.put("thisYearBalance", thisYearBalance);
        thisYearData.put("thisYearIncomePercent", thisYearIncomePercent);
        thisYearData.put("thisYearExpensePercent", thisYearExpensePercent);
        //封装上个月结果
        JSONObject lastYearData = new JSONObject();
        lastYearData.put("lastYearTotal", lastYearTotal);
        lastYearData.put("lastYearIncome", lastYearIncome);
        lastYearData.put("lastYearIncomeRecord", lastYearIncomeRecord);
        lastYearData.put("lastYearExpense", lastYearExpense);
        lastYearData.put("lastYearExpenseRecord", lastYearExpenseRecord);
        lastYearData.put("lastYearBalance", lastYearBalance);
        lastYearData.put("lastYearIncomePercent", lastYearIncomePercent);
        lastYearData.put("lastYearExpensePercent", lastYearExpensePercent);
        //封装这总结果
        JSONObject analysisData = new JSONObject();
        analysisData.put("thisMonthData", thisYearData);
        analysisData.put("lastMonthData", lastYearData);

        return RetResult.success(analysisData);
    }


}
