package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.Announcement;
import com.mzl.incomeexpensemanagesystem.entity.Feedback;
import com.mzl.incomeexpensemanagesystem.mapper.FeedbackMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.vo.FeedbackVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2022-02-06
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private UserService userService;

    /**
     * 提交用户反馈
     * @param feedback
     * @return
     */
    @Override
    public RetResult postFeedback(Feedback feedback) {
        feedback.setUserId(userService.getUserId());
        Date now = new Date();
        feedback.setCreateTime(now);
        feedbackMapper.insert(feedback);
        return RetResult.success();
    }

    /**
     * 分页模糊查询用户反馈(管理员)
     * @param feedbackVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageFeedback(FeedbackVo feedbackVo, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        IPage<FeedbackVo> page = new Page<>(currentPage, pageSize);
        IPage<FeedbackVo> feedbackList= feedbackMapper.selectPageFeedback(page, feedbackVo);
        return RetResult.success(feedbackList);
    }

    /**
     * 收藏用户反馈(管理员)
     * @param feedbackId
     * @return
     */
    @Override
    public RetResult collectFeedback(Integer feedbackId) {
        feedbackMapper.collectFeedback(feedbackId);
        return RetResult.success();
    }

    /**
     * 取消收藏用户反馈(管理员)
     * @param feedbackId
     * @return
     */
    @Override
    public RetResult unCollectFeedback(Integer feedbackId) {
        feedbackMapper.unCollectFeedback(feedbackId);
        return RetResult.success();
    }


}
