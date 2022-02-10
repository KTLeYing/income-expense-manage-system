package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzl.incomeexpensemanagesystem.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzl.incomeexpensemanagesystem.vo.FeedbackVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2022-02-06
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

    /**
     * 分页模糊查询用户反馈(管理员)
     * @param page
     * @param feedbackVo
     * @return
     */
    IPage<FeedbackVo> selectPageFeedback(IPage<FeedbackVo> page, FeedbackVo feedbackVo);

    /**
     * 收藏用户反馈(管理员)
     * @param feedbackId
     */
    void collectFeedback(Integer feedbackId);

    /**
     * 取消收藏用户反馈(管理员)
     * @param feedbackId
     */
    void unCollectFeedback(Integer feedbackId);
}
