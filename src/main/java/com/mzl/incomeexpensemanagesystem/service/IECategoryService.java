package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;

/**
 * <p>
 * 收支类型 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface IECategoryService extends IService<IECategory> {

    /**
     * 添加收支类型
     * @param ieCategory
     * @return
     */
    RetResult addCategory(IECategory ieCategory);

    /**
     * 删除收支类型
     * @param id
     * @return
     */
    RetResult deleteCategory(Integer id);

    /**
     * 修改收支类型
     * @param ieCategory
     * @return
     */
    RetResult updateCategory(IECategory ieCategory);

    /**
     * 查询所有收支类型
     * @param
     * @return
     */
    RetResult selectAllCategory();

    /**
     * 分页模糊查询当前用户收支类型
     * @param ieCategory
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageCategory(IECategory ieCategory, Integer currentPage, Integer pageSize);

    /**
     * 批量删除收支类型
     * @param ids
     * @return
     */
    RetResult deleteBatchCategory(Integer[] ids);

}
