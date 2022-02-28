package com.mzl.incomeexpensemanagesystem.controller.manage;

import com.mzl.incomeexpensemanagesystem.entity.*;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.*;
import com.mzl.incomeexpensemanagesystem.vo.FeedbackVo;
import com.mzl.incomeexpensemanagesystem.vo.IERecordVo;
import com.mzl.incomeexpensemanagesystem.vo.MemorandumVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: v_ktlema
 * @CreateDate: 2021/12/20 21:41
 * @Version: 1.0
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/system")
@Api(value = "系统模块接口", tags = "系统模块接口")
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private IERecordService ieRecordService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private FeedbackService feedbackService;


    /****************用户管理模块***********************/
    @GetMapping("/selectPageUser")
    @ApiOperation(value = "分页模糊查询用户(管理员)")
    public RetResult selectPageUser(User user, Integer currentPage, Integer pageSize){
        return userService.selectPageUser(user, currentPage, pageSize);
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户(管理员)")
    public RetResult addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "修改用户(管理员)")
    public RetResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/banUser")
    @ApiOperation(value = "禁用用户(管理员)")
    public RetResult banUser(Integer userId){
        return userService.banUser(userId);
    }

    @GetMapping("/banBatchUser")
    @ApiOperation(value = "批量禁用用户(管理员)")
    public RetResult banBatchUser(Integer[] userIds){
        return userService.banBatchUser(userIds);
    }

    @GetMapping("/unBanUser")
    @ApiOperation(value = "解禁用户(管理员)")
    public RetResult unBanUser(Integer userId){
        return userService.unBanUser(userId);
    }

    @GetMapping("/unBanBatchUser")
    @ApiOperation(value = "批量解禁用户(管理员)")
    public RetResult unBanBatchUser(Integer[] userIds){
        return userService.unBanBatchUser(userIds);
    }

    @GetMapping("/deleteUser")
    @ApiOperation(value = "删除用户(管理员)")
    public RetResult deleteUser(Integer userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("/deleteBatchUser")
    @ApiOperation(value = "批量删除用户(管理员)")
    public RetResult deleteBatchUser(Integer[] userIds){
        return userService.deleteBatchUser(userIds);
    }

    @PostMapping(value = "/exportAllUser", produces = "application/octet-stream")
    @ApiOperation(value = "导出所有用户信息Excel(管理员)")
    public void exportAllUser(HttpServletResponse response){
        userService.exportAllUser(response);
    }


    /****************收支记录管理模块***********************/
    @GetMapping("/selectPageRecord")
    @ApiOperation(value = "分页模糊查询收支记录(管理员)")
    public RetResult selectPageRecord(IERecordVo ieRecordVo, Integer currentPage, Integer pageSize) throws ParseException {
        return ieRecordService.selectPageRecordAD(ieRecordVo, currentPage, pageSize);
    }

    @PostMapping("/addRecord")
    @ApiOperation(value = "添加收支记录(管理员)")
    public RetResult addRecord(@RequestBody IERecord ieRecord){
        return ieRecordService.addRecordAD(ieRecord);
    }

    @PostMapping("/updateRecord")
    @ApiOperation(value = "修改收支记录(管理员)")
    public RetResult updateUser(@RequestBody IERecord ieRecord){
        return ieRecordService.updateRecordAD(ieRecord);
    }

    @GetMapping("/deleteRecord")
    @ApiOperation(value = "删除收支记录(管理员)")
    public RetResult deleteRecord(Integer id){
        return ieRecordService.deleteRecord(id);
    }

    @GetMapping("/deleteBatchRecord")
    @ApiOperation(value = "批量删除收支记录(管理员)")
    public RetResult deleteBatchRecord(Integer[] ids){
        return ieRecordService.deleteBatchRecord(ids);
    }

    @PostMapping(value = "/exportAllRecord", produces = "application/octet-stream")
    @ApiOperation(value = "导出所有用户收支记录Excel(管理员)")
    public void exportAllRecord(HttpServletResponse response){
        ieRecordService.exportAllRecordAD(response);
    }


    /****************新闻管理模块***********************/
    @GetMapping("/selectPageNews")
    @ApiOperation(value = "分页模糊查询新闻(管理员)")
    public RetResult selectPageNews(News news, Integer currentPage, Integer pageSize) throws ParseException {
        return newsService.selectPageNews(news, currentPage, pageSize);
    }

    @PostMapping("/addNews")
    @ApiOperation(value = "添加新闻(管理员)")
    public RetResult addNews(@RequestBody News news){
        return newsService.addNews(news);
    }

    @PostMapping("/updateNews")
    @ApiOperation(value = "修改新闻(管理员)")
    public RetResult updateNews(@RequestBody News news){
        return newsService.updateNews(news);
    }

    @GetMapping("/deleteNews")
    @ApiOperation(value = "删除新闻(管理员)")
    public RetResult deleteNews(Integer id){
        return newsService.deleteNews(id);
    }

    @GetMapping("/deleteBatchNews")
    @ApiOperation(value = "批量删除新闻(管理员)")
    public RetResult deleteBatchNews(Integer[] ids){
        return newsService.deleteBatchNews(ids);
    }


    /****************公告管理模块***********************/
    @GetMapping("/selectPageAnnouncement")
    @ApiOperation(value = "分页模糊查询公告(管理员)")
    public RetResult selectPageAnnouncement(Announcement announcement, Integer currentPage, Integer pageSize) throws ParseException {
        return announcementService.selectPageAnnouncement(announcement, currentPage, pageSize);
    }

    @PostMapping("/addAnnouncement")
    @ApiOperation(value = "添加公告(管理员)")
    public RetResult addAnnouncement(@RequestBody Announcement announcement){
        return announcementService.addAnnouncement(announcement);
    }

    @PostMapping("/updateAnnouncement")
    @ApiOperation(value = "修改公告(管理员)")
    public RetResult updateAnnouncement(@RequestBody Announcement announcement){
        return announcementService.updateAnnouncement(announcement);
    }

    @GetMapping("/deleteAnnouncement")
    @ApiOperation(value = "删除公告(管理员)")
    public RetResult deleteAnnouncement(Integer id){
        return announcementService.deleteAnnouncement(id);
    }

    @GetMapping("/deleteBatchAnnouncement")
    @ApiOperation(value = "批量删除公告(管理员)")
    public RetResult deleteBatchAnnouncement(Integer[] ids){
        return announcementService.deleteBatchAnnouncement(ids);
    }


    /****************用户反馈模块***********************/
    @GetMapping("/selectPageFeedback")
    @ApiOperation(value = "分页模糊查询用户反馈(管理员)")
    public RetResult selectPageFeedback(FeedbackVo feedbackVo, Integer currentPage, Integer pageSize){
        return feedbackService.selectPageFeedback(feedbackVo, currentPage, pageSize);
    }

    @GetMapping("/collectFeedback")
    @ApiOperation(value = "收藏用户反馈(管理员)")
    public RetResult collectFeedback(Integer feedbackId){
        return feedbackService.collectFeedback(feedbackId);
    }

    @GetMapping("/unCollectFeedback")
    @ApiOperation(value = "取消收藏用户反馈(管理员)")
    public RetResult unCollectFeedback(Integer feedbackId){
        return feedbackService.unCollectFeedback(feedbackId);
    }


    /****************系统数据监控模块***********************/
    @GetMapping("/statisticUserActive")
    @ApiOperation(value = "统计今天、本周、历史用户激活数(管理员)")
    public RetResult statisticUserActive(){
        return userService.statisticUserActive();
    }

    @GetMapping("/tenYearUserActive")
    @ApiOperation(value = "近10年用户激活数对比(管理员)")
    public RetResult tenYearUserActive(){
        return userService.tenYearUserActive();
    }

    @GetMapping("/tenFeedbackUser")
    @ApiOperation(value = "反馈活跃的前10用户(管理员)")
    public RetResult tenFeedbackUser(){
        return userService.tenFeedbackUser();
    }


    /****************系统性能监控模块***********************/
    //使用actuator监控+adminUI可视化监控数据
    /**
     * 总体监控：http://laptop-fk4agt9c.mshome.net:8888/incomeExpense
     */

    /**
     * 性能监控：http://laptop-fk4agt9c.mshome.net:8888/incomeExpense/instances/0e100c5bc670/details
     */

    /**
     * 环境配置：http://laptop-fk4agt9c.mshome.net:8888/incomeExpense/instances/0e100c5bc670/env
     */

    /**
     * 配置属性：http://laptop-fk4agt9c.mshome.net:8888/incomeExpense/instances/0e100c5bc670/configprops
     */

    /**
     * JVM监控：http://laptop-fk4agt9c.mshome.net:8888/incomeExpense/instances/0e100c5bc670/jolokia?domain=DefaultDomain&view
     */

    /**
     * HTTP跟踪：http://laptop-fk4agt9c.mshome.net:8888/incomeExpense/instances/0e100c5bc670/httptrace
     */

    /**
     * 缓存监控：http://laptop-fk4agt9c.mshome.net:8888/incomeExpense/instances/0e100c5bc670/caches
     */

}
