package com.mzl.incomeexpensemanagesystem.controller.common;


import com.mzl.incomeexpensemanagesystem.entity.Budget;
import com.mzl.incomeexpensemanagesystem.entity.WishList;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.WishListService;
import com.mzl.incomeexpensemanagesystem.vo.WishListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 心愿单表 前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/wishList")
@Api(value = "心愿单模块接口", tags = "心愿单模块接口")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @PostMapping("/addWish")
    @ApiOperation(value = "添加心愿单")
    public RetResult addWish(@RequestBody WishList wishList){
        return wishListService.addWish(wishList);
    }

    @PostMapping("/updateWish")
    @ApiOperation(value = "修改心愿单")
    public RetResult updateWish(@RequestBody WishList wishList){
        return wishListService.updateWish(wishList);
    }

    @GetMapping("/deleteWish")
    @ApiOperation(value = "删除心愿单")
    public RetResult deleteWish(Integer id){
        return wishListService.deleteWish(id);
    }

    @GetMapping("/deleteBatchWish")
    @ApiOperation(value = "批量删除心愿单")
    public RetResult deleteBatchWish(Integer[] ids){
        return wishListService.deleteBatchWish(ids);
    }

    @GetMapping("/selectPageWish")
    @ApiOperation(value = "分页模糊查询心愿单")
    public RetResult selectPageWish(WishListVo wishListVo, Integer currentPage, Integer pageSize){
        return wishListService.selectPageWish(wishListVo, currentPage, pageSize);
    }

}

