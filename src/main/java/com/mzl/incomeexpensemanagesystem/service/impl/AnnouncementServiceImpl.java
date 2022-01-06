package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.Announcement;
import com.mzl.incomeexpensemanagesystem.mapper.AnnouncementMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.AnnouncementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2022-01-06
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    /**
     * 分页模糊查询公告
     * @param announcement
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageAnnouncement(Announcement announcement, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(announcement.getTitle()), "title", announcement.getTitle());
        IPage<Announcement> page = new Page<>(currentPage, pageSize);
        IPage<Announcement> announcementList = announcementMapper.selectPage(page, queryWrapper);
        return RetResult.success(announcementList);
    }

    @Override
    public RetResult lookAnnouncementDetail(Integer announcementId) {
        Announcement announcement = announcementMapper.selectById(announcementId);
        return RetResult.success(announcement);
    }

    /**
     * 获取最新公告(Top5)
     * @return
     */
    @Override
    public RetResult selectNewAnnouncement() {
        List<Announcement> announcementList = announcementMapper.selectNewAnnouncement();
        return RetResult.success(announcementList);
    }

}
