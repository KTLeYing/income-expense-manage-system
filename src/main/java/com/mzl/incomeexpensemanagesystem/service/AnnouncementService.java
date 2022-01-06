package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2022-01-06
 */
public interface AnnouncementService extends IService<Announcement> {

    /**
     * 分页模糊查询公告
     * @param announcement
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageAnnouncement(Announcement announcement, Integer currentPage, Integer pageSize);

    /**
     * lookAnnouncementDetail
     * @param announcementId
     * @return
     */
    RetResult lookAnnouncementDetail(Integer announcementId);

    /**
     * 获取最新公告(Top5)
     * @return
     */
    RetResult selectNewAnnouncement();

}
