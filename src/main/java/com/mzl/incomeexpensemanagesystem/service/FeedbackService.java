package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.FeedbackVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2022-02-06
 */
public interface FeedbackService extends IService<Feedback> {

    /**
     * 提交用户反馈
     * @param feedback
     * @return
     */
    RetResult postFeedback(Feedback feedback);

    /**
     * 分页模糊查询用户反馈(管理员)
     * @param feedbackVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageFeedback(FeedbackVo feedbackVo, Integer currentPage, Integer pageSize);

    /**
     * 收藏用户反馈(管理员)
     * @param feedbackId
     * @return
     */
    RetResult collectFeedback(Integer feedbackId);

    /**
     * 取消收藏用户反馈(管理员)
     * @param feedbackId
     * @return
     */
    RetResult unCollectFeedback(Integer feedbackId);
}
