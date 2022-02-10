package com.mzl.incomeexpensemanagesystem.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName :   ClearUserActiveTask
 * @Description: 定时重置本周用户激活数redis定时任务（这里使用动态定时任务框架Quartz）
 * @Author: mzl
 * @CreateDate: 2022/2/9 11:23
 * @Version: 1.0
 */
@Slf4j
public class ResetUserThisWeekActiveTask extends QuartzJobBean {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户本周激活数的key
     */
    private static final String USER_ACTIVE_THISWEEK_KEY = "incomeExpense:userActive:thisWeek";

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 核心任务逻辑,定时重置本周用户激活数redis
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("定时重置本周用户激活数redis=====>" + "ResetUserThisWeekActiveTask Start---" + simpleDateFormat.format(new Date()));
        //重置本周的用户激活数redis
        redisTemplate.opsForValue().set(USER_ACTIVE_THISWEEK_KEY, 0);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("定时重置本周用户激活数redis=====>" + "ResetUserThisWeekActiveTask End---" + simpleDateFormat.format(new Date()));
    }

}
