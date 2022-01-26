package com.mzl.incomeexpensemanagesystem.controller.manage;

import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.MemorandumService;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.vo.MemorandumVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: v_ktlema
 * @CreateDate: 2021/12/20 21:41
 * @Version: 1.0
 */
@RestController
@RequestMapping("/system")
@Api(value = "系统模块接口", tags = "系统模块接口")
public class SystemController {

    @Autowired
    private UserService userService;

    /****************用户管理模块***********************/
    @GetMapping("/selectPageUser")
    @ApiOperation(value = "分页模糊查询用户(管理员)")
    public RetResult selectPageUser(User user, Integer currentPage, Integer pageSize){
        return userService.selectPageUser(user, currentPage, pageSize);
    }


    /****************收支记录管理模块***********************/




    /****************新闻管理模块***********************/




    /****************公告管理模块***********************/




    /****************系统数据监控模块***********************/




    /****************系统性能监控模块***********************/


}
