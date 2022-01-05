package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzl.incomeexpensemanagesystem.entity.WishList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzl.incomeexpensemanagesystem.vo.WishListVo;

/**
 * <p>
 * 心愿单表 Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface WishListMapper extends BaseMapper<WishList> {

    /**
     * 分页模糊查询心愿单
     * @param page
     * @param wishListVo
     * @return
     */
    IPage<WishListVo> selectPageWish(IPage<WishListVo> page, WishListVo wishListVo);

}
