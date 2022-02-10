package com.mzl.incomeexpensemanagesystem.controller.common;


import com.mzl.incomeexpensemanagesystem.entity.Feedback;
import com.mzl.incomeexpensemanagesystem.entity.IECategory;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author v_ktlema
 * @since 2022-02-06
 */
@RestController
@RequestMapping("/feedback")
@Api(value = "用户反馈模块接口", tags = "用户反馈模块接口")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/postFeedback")
    @ApiOperation(value = "提交用户反馈")
    public RetResult postFeedback(@RequestBody Feedback feedback){
        return feedbackService.postFeedback(feedback);
    }

}

