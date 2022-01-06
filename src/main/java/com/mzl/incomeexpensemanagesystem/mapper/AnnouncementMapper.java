package com.mzl.incomeexpensemanagesystem.mapper;

import com.mzl.incomeexpensemanagesystem.entity.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2022-01-06
 */
public interface AnnouncementMapper extends BaseMapper<Announcement> {

    /**
     * 获取最新公告(Top5)
     * @return
     */
    List<Announcement> selectNewAnnouncement();

}
