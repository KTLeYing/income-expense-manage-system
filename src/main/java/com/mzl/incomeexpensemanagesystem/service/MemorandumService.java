package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.Memorandum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.MemorandumVo;

/**
 * <p>
 * 备忘录表 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface MemorandumService extends IService<Memorandum> {

    /**
     * 添加备忘录
     * @param memorandum
     * @return
     */
    RetResult addMemorandum(Memorandum memorandum);

    /**
     * 修改备忘录
     * @param memorandum
     * @return
     */
    RetResult updateMemorandum(Memorandum memorandum);

    /**
     * 删除备忘录
     * @param id
     * @return
     */
    RetResult deleteMemorandum(Integer id);

    /**
     * 分页模糊查询备忘录
     * @param memorandumVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageMemorandum(MemorandumVo memorandumVo, Integer currentPage, Integer pageSize);
}
