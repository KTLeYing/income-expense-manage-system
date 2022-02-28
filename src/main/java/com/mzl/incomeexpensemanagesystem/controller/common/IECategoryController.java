package com.mzl.incomeexpensemanagesystem.controller.common;


import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.IECategoryService;
import com.mzl.incomeexpensemanagesystem.service.IERecordService;
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 收支类型 前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/iECategory")
@Api(value = "收支类型模块接口", tags = "收支类型模块接口")
public class IECategoryController {

    @Autowired
    private IECategoryService ieCategoryService;

    @PostMapping("/addCategory")
    @ApiOperation(value = "添加收支类型")
    public RetResult addCategory(@RequestBody IECategory ieCategory){
        return ieCategoryService.addCategory(ieCategory);
    }

    @GetMapping("/deleteCategory")
    @ApiOperation(value = "删除收支类型")
    public RetResult deleteCategory(Integer id){
        return ieCategoryService.deleteCategory(id);
    }

    @GetMapping("/deleteBatchCategory")
    @ApiOperation(value = "批量删除收支类型")
    public RetResult deleteBatchCategory(Integer[] ids){
        return ieCategoryService.deleteBatchCategory(ids);
    }

    @PostMapping("/updateCategory")
    @ApiOperation(value = "修改收支类型")
    public RetResult updateCategory(@RequestBody IECategory ieCategory){
        return ieCategoryService.updateCategory(ieCategory);
    }

    @GetMapping("/selectAllCategory")
    @ApiOperation(value = "查询当前用户所有收支类型")
    public RetResult selectAllCategory(){
        return ieCategoryService.selectAllCategory();
    }

    @GetMapping("/selectPageCategory")
    @ApiOperation(value = "分页模糊查询当前用户收支类型")
    public RetResult selectPageCategory(IECategory ieCategory, Integer currentPage, Integer pageSize){
        return ieCategoryService.selectPageCategory(ieCategory, currentPage, pageSize);
    }

}

