package com.mzl.incomeexpensemanagesystem.controller.common;


import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.entity.IERecord;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.IERecordService;
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * <p>
 * 收支记录表 前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/iERecord")
@Api(value = "收支记录模块接口", tags = "收支记录模块接口")
public class IERecordController {

    @Autowired
    private IERecordService ieRecordService;

    @PostMapping("/addRecord")
    @ApiOperation(value = "添加收支记录")
    public RetResult addRecord(@RequestBody IERecord ieRecord){
        return ieRecordService.addRecord(ieRecord);
    }

    @GetMapping("/deleteRecord")
    @ApiOperation(value = "删除收支记录")
    public RetResult deleteRecord(Integer id){
        return ieRecordService.deleteRecord(id);
    }

    @GetMapping("/deleteBatchRecord")
    @ApiOperation(value = "批量删除收支记录")
    public RetResult deleteBatchRecord(Integer[] ids){
        return ieRecordService.deleteBatchRecord(ids);
    }

    @PostMapping("/updateRecord")
    @ApiOperation(value = "修改收支记录")
    public RetResult updateRecord(@RequestBody IERecord ieRecord){
        return ieRecordService.updateRecord(ieRecord);
    }

    @GetMapping("/selectAllRecord")
    @ApiOperation(value = "查询当前用户所有收支记录")
    public RetResult selectAllRecord(){
        return ieRecordService.selectAllRecord();
    }

    @GetMapping("/selectPageRecord")
    @ApiOperation(value = "分页模糊查询当前用户收支记录")
    public RetResult selectPageRecord(IERecordVo ieRecordVo, Integer currentPage, Integer pageSize) throws ParseException {
        return ieRecordService.selectPageRecord(ieRecordVo, currentPage, pageSize);
    }

    @PostMapping(value = "/exportAllRecord", produces = "application/octet-stream")
    @ApiOperation(value = "导出当前用户所有收支记录Excel")
    public void exportAllRecord(HttpServletResponse response){
        ieRecordService.exportAllRecord(response);
    }

}

