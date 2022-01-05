package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzl.incomeexpensemanagesystem.entity.Memorandum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzl.incomeexpensemanagesystem.vo.MemorandumVo;

/**
 * <p>
 * 备忘录表 Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface MemorandumMapper extends BaseMapper<Memorandum> {

    /**
     * 分页模糊查询备忘录
     * @param page
     * @param memorandumVo
     * @return
     */
    IPage<MemorandumVo> selectPageMemorandum(IPage<MemorandumVo> page, MemorandumVo memorandumVo);
}
