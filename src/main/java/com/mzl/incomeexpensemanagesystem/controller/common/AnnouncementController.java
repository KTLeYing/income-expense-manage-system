package com.mzl.incomeexpensemanagesystem.controller.common;


import com.mzl.incomeexpensemanagesystem.entity.Announcement;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.AnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2022-01-06
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/announcement")
@Api(value = "公告模块接口", tags = "公告模块接口")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/selectPageAnnouncement")
    @ApiOperation(value = "分页模糊查询公告")
    public RetResult selectPageAnnouncement(Announcement announcement, Integer currentPage, Integer pageSize){
        return announcementService.selectPageAnnouncement(announcement, currentPage, pageSize);
    }

    @GetMapping("/lookAnnouncementDetail")
    @ApiOperation(value = "查看公告详情")
    public RetResult lookAnnouncementDetail(Integer announcementId){
        return announcementService.lookAnnouncementDetail(announcementId);
    }

    @GetMapping("/selectNewAnnouncement")
    @ApiOperation(value = "获取最新公告(Top5)")
    public RetResult selectNewAnnouncement(){
        return announcementService.selectNewAnnouncement();
    }

}

