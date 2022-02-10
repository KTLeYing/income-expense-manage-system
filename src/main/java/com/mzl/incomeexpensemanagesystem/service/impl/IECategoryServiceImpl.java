package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.mapper.IECategoryMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.IECategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 收支类型 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
@Slf4j
@Transactional
public class IECategoryServiceImpl extends ServiceImpl<IECategoryMapper, IECategory> implements IECategoryService {

    @Autowired
    private IECategoryMapper ieCategoryMapper;

    @Autowired
    private UserService userService;

    /**
     * 添加收支类型
     * @param ieCategory
     * @return
     */
    @Override
    public RetResult addCategory(IECategory ieCategory) {
        //查看子类型是否存在
        QueryWrapper<IECategory> ieCategoryQueryWrapper = new QueryWrapper<>();
        ieCategoryQueryWrapper.eq("son_category", ieCategory.getSonCategory());
        ieCategoryQueryWrapper.eq("parent_category", ieCategory.getParentCategory());
        IECategory ieCategory1 = ieCategoryMapper.selectOne(ieCategoryQueryWrapper);
        if (ieCategory1 != null){
            return RetResult.fail(RetCodeEnum.SON_CATEGORY_EXISTS);
        }
        ieCategory.setUserId(userService.getUserId());
        ieCategoryMapper.insert(ieCategory);
        return RetResult.success();
    }

    /**
     * 删除收支类型
     * @param id
     * @return
     */
    @Override
    public RetResult deleteCategory(Integer id) {
        ieCategoryMapper.deleteById(id);
        return RetResult.success();
    }

    /**
     * 修改收支类型
     * @param ieCategory
     * @return
     */
    @Override
    public RetResult updateCategory(IECategory ieCategory) {
        //查看子类型是否存在
        QueryWrapper<IECategory> ieCategoryQueryWrapper = new QueryWrapper<>();
        ieCategoryQueryWrapper.eq("son_category", ieCategory.getSonCategory());
        ieCategoryQueryWrapper.eq("parent_category", ieCategory.getParentCategory());
        IECategory ieCategory1 = ieCategoryMapper.selectOne(ieCategoryQueryWrapper);
        if (ieCategory1 != null){
            return RetResult.fail(RetCodeEnum.SON_CATEGORY_EXISTS);
        }
        ieCategory.setUserId(userService.getUserId());
        ieCategoryMapper.updateById(ieCategory);
        return RetResult.success();
    }

    /**
     * 查询当前用户所有收支类型
     * @param
     * @return
     */
    @Override
    public RetResult selectAllCategory() {
        QueryWrapper<IECategory> queryWrapper = new QueryWrapper<>();
        //获取当前用户
        Integer userId = userService.getUserId();
        queryWrapper.eq("user_id", userId);
        List<IECategory> categoryList = ieCategoryMapper.selectList(queryWrapper);
        return RetResult.success(categoryList);
    }

    /**
     * 分页模糊查询当前用户收支类型
     * @param ieCategory
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageCategory(IECategory ieCategory, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            //不传默认为第一页
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            //不传默认10条
            pageSize = 10;
        }
        QueryWrapper<IECategory> queryWrapper = new QueryWrapper<>();
        //获取当前用户
        Integer userId = userService.getUserId();
        queryWrapper.eq("user_id", userId);
        //QueryWrapper条件构造器从上到下执行，如果后面有条件时 且 没自己没设置其他拼接函数时，默认以 and 来链接（除非自己设置了and、or等拼接函数），不调用or则默认为使用 and 连
        queryWrapper.like(!StringUtils.isEmpty(ieCategory.getParentCategory()), "parent_category", ieCategory.getParentCategory());
        queryWrapper.like(!StringUtils.isEmpty(ieCategory.getSonCategory()), "son_category", ieCategory.getSonCategory());
        IPage<IECategory> page = new Page<>(currentPage, pageSize);
        IPage<IECategory> ieCategoryIPage = ieCategoryMapper.selectPage(page, queryWrapper);
        log.info("分页模糊查询当前用户收支类型=====>" + "收支类型分页结果：" + ieCategoryIPage.getRecords());
        return RetResult.success(ieCategoryIPage);
    }

    /**
     * 批量删除收支类型
     * @param ids
     * @return
     */
    @Override
    public RetResult deleteBatchCategory(Integer[] ids) {
        List<Integer> idsList = Arrays.stream(ids).collect(Collectors.toList());
        ieCategoryMapper.deleteBatchIds(idsList);
        return RetResult.success();
    }

}
