package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.Memorandum;
import com.mzl.incomeexpensemanagesystem.mapper.MemorandumMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.MemorandumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.vo.MemorandumVo;
import com.mzl.incomeexpensemanagesystem.vo.WishListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 备忘录表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
public class MemorandumServiceImpl extends ServiceImpl<MemorandumMapper, Memorandum> implements MemorandumService {

    @Autowired
    private UserService userService;

    @Autowired
    private MemorandumMapper memorandumMapper;

    /**
     * 添加备忘录
     * @param memorandum
     * @return
     */
    @Override
    public RetResult addMemorandum(Memorandum memorandum) {
        memorandum.setUserId(userService.getUserId());
        Date now = new Date();
        memorandum.setCreateTime(now);
        memorandumMapper.insert(memorandum);
        return RetResult.success();
    }

    /**
     * 修改备忘录
     * @param memorandum
     * @return
     */
    @Override
    public RetResult updateMemorandum(Memorandum memorandum) {
        memorandum.setUserId(userService.getUserId());
        memorandumMapper.updateById(memorandum);
        return RetResult.success();
    }

    /**
     * 删除备忘录
     * @param id
     * @return
     */
    @Override
    public RetResult deleteMemorandum(Integer id) {
        memorandumMapper.deleteById(id);
        return RetResult.success();
    }

    /**
     * 批量删除备忘录
     * @param ids
     * @return
     */
    @Override
    public RetResult deleteBatchMemorandum(Integer[] ids) {
        List<Integer> idList = Arrays.stream(ids).collect(Collectors.toList());
        memorandumMapper.deleteBatchIds(idList);
        return RetResult.success();
    }

    /**
     * 分页模糊查询备忘录
     * @param memorandumVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageMemorandum(MemorandumVo memorandumVo, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        memorandumVo.setUserId(userService.getUserId());
        //分页查询
        IPage<MemorandumVo> page = new Page<>(currentPage, pageSize);
        IPage<MemorandumVo> wishListPage = memorandumMapper.selectPageMemorandum(page, memorandumVo);
        return RetResult.success(wishListPage);
    }


}
