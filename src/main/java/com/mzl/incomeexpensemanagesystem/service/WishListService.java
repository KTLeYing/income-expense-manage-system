package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.WishList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.WishListVo;

/**
 * <p>
 * 心愿单表 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface WishListService extends IService<WishList> {

    /**
     * 添加心愿单
     * @param wishList
     * @return
     */
    RetResult addWish(WishList wishList);

    /**
     * 修改心愿单
     * @param wishList
     * @return
     */
    RetResult updateWish(WishList wishList);

    /**
     * deleteWish
     * @param id
     * @return
     */
    RetResult deleteWish(Integer id);

    /**
     * 分页模糊查询心愿单
     * @param wishListVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageWish(WishListVo wishListVo, Integer currentPage, Integer pageSize);
}
