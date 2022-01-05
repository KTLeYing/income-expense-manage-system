package com.mzl.incomeexpensemanagesystem.controller.common;


import com.mzl.incomeexpensemanagesystem.entity.Memorandum;
import com.mzl.incomeexpensemanagesystem.entity.WishList;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.MemorandumService;
import com.mzl.incomeexpensemanagesystem.vo.MemorandumVo;
import com.mzl.incomeexpensemanagesystem.vo.WishListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 备忘录表 前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/memorandum")
@Api(value = "新备忘录块接口", tags = "备忘录模块接口")
public class MemorandumController {

    @Autowired
    private MemorandumService memorandumService;

    @PostMapping("/addMemorandum")
    @ApiOperation(value = "添加备忘录")
    public RetResult addMemorandum(@RequestBody Memorandum memorandum){
        return memorandumService.addMemorandum(memorandum);
    }

    @PostMapping("/updateMemorandum")
    @ApiOperation(value = "修改备忘录")
    public RetResult updateMemorandum(@RequestBody Memorandum memorandum){
        return memorandumService.updateMemorandum(memorandum);
    }

    @GetMapping("/deleteMemorandum")
    @ApiOperation(value = "删除备忘录")
    public RetResult deleteMemorandum(Integer id){
        return memorandumService.deleteMemorandum(id);
    }

    @GetMapping("/selectPageMemorandum")
    @ApiOperation(value = "分页模糊查询备忘录")
    public RetResult selectPageMemorandum(MemorandumVo memorandumVo, Integer currentPage, Integer pageSize){
        return memorandumService.selectPageMemorandum(memorandumVo, currentPage, pageSize);
    }

}

