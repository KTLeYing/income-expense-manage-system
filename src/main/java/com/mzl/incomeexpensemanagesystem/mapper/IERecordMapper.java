package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

}
