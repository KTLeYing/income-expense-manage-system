package com.mzl.incomeexpensemanagesystem.schedule;

import com.mzl.incomeexpensemanagesystem.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   ScheduleJob
 * @Description: 定时任务（这里使用动态定时任务框架Quartz）
 * @Author: v_ktlema
 * @CreateDate: 2022/1/6 11:10
 * @Version: 1.0
 */
@Slf4j
public class NewsCollectTask extends QuartzJobBean {

    @Autowired
    private NewsService newsService;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 核心任务逻辑,将redis中的数据同步刷到数据库中
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("redis中的数据同步刷到数据库=====>" + "NewsCollectTask Start---" + simpleDateFormat.format(new Date()));
        //同步redis用户收藏新闻数据到数据库
        newsService.transCollectNewsFromRedisToDb();
        //同步redis新闻收藏数到数据库
        newsService.transCollectNewsCountFromRedisToDb();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("redis中的数据同步刷到数据库=====>" + "NewsCollectTask End---" + simpleDateFormat.format(new Date()));
    }

}
