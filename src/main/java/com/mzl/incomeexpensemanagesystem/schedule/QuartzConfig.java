package com.mzl.incomeexpensemanagesystem.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   QuartzConfig
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2022/1/6 11:13
 * @Version: 1.0
 */
@Slf4j
@Configuration
public class QuartzConfig  {

    /**
     * 定时任务的标识
     */
    private static final String COLLECT_NEWS_TASK_IDENTITY = "CollectNewsTaskQuartz";

    @Bean
    public JobDetail quartzDetail(){
        return JobBuilder.newJob(NewsCollectTask.class).withIdentity(COLLECT_NEWS_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger quartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10) //设置时间周期单位秒，每10秒执行一次
//                .withIntervalInHours(2) //两个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(quartzDetail())
                .withIdentity(COLLECT_NEWS_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }



}
