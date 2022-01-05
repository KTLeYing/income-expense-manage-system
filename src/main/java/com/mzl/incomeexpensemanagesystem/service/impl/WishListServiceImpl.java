package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.WishList;
import com.mzl.incomeexpensemanagesystem.mapper.WishListMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.service.WishListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.vo.WishListVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 心愿单表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
public class WishListServiceImpl extends ServiceImpl<WishListMapper, WishList> implements WishListService {

    @Autowired
    private WishListMapper wishListMapper;

    @Autowired
    private UserService userService;

    /**
     * 添加心愿单
     * @param wishList
     * @return
     */
    @Override
    public RetResult addWish(WishList wishList) {
        Date now = new Date();
        wishList.setCreateTime(now);
        wishList.setUserId(userService.getUserId());
        wishList.setState(1);
        wishListMapper.insert(wishList);
        return RetResult.success();
    }

    /**
     * 修改心愿单
     * @param wishList
     * @return
     */
    @Override
    public RetResult updateWish(WishList wishList) {
        wishList.setUserId(userService.getUserId());
        wishListMapper.updateById(wishList);
        return RetResult.success();
    }

    /**
     * 删除心愿单
     * @param id
     * @return
     */
    @Override
    public RetResult deleteWish(Integer id) {
        wishListMapper.deleteById(id);
        return RetResult.success();
    }

    /**
     * 分页模糊查询心愿单
     * @param wishListVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageWish(WishListVo wishListVo, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        wishListVo.setUserId(userService.getUserId());
        //分页查询
        IPage<WishListVo> page = new Page<>(currentPage, pageSize);
        IPage<WishListVo> wishListPage = wishListMapper.selectPageWish(page, wishListVo);
        return RetResult.success(wishListPage);
    }


}
