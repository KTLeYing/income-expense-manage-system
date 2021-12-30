package com.mzl.incomeexpensemanagesystem.service.impl;

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
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;
import com.mzl.incomeexpensemanagesystem.vo.StatisticVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
     * 根据年份统计
     * @param year
     * @return
     */
    @Override
    public RetResult statisticByYear(String year) {
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
     * 根据年-月份统计各子类收支
     * @param time
     * @return
     */
    @Override
    public RetResult statisticSonCategory(String time) {
        String parentCategory = "收入";
        //统计收入的个子类占比

        //统计支出的个子类占比
        parentCategory = "支出";

        return RetResult.success();
    }

}
