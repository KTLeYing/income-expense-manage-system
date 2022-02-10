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
     * 新闻收藏数据库-redis同步定时任务的标识
     */
    private static final String COLLECT_NEWS_TASK_IDENTITY = "CollectNewsTaskQuartz";

    /**
     * 定时重置今天用户激活数redis定时任务的标识
     */
    private static final String RESET_USER_TODAY_ACTIVE_TASK_IDENTITY = "ResetUserTodayActiveTaskQuartz";

    /**
     * 定时重置本周用户激活数redis定时任务的标识
     */
    private static final String RESET_USER_THISWEEK_ACTIVE_TASK_IDENTITY = "ResetUserThisWeekActiveTaskQuartz";

    /**
     * 新闻收藏数据库-redis同步定时任务配置
     * @return
     */
    @Bean
    public JobDetail collectNewsQuartzDetail(){
        return JobBuilder.newJob(NewsCollectTask.class).withIdentity(COLLECT_NEWS_TASK_IDENTITY).storeDurably().build();
    }

    /**
     * 新闻收藏数据库-redis同步定时任务触发器
     * @return
     */
    @Bean
    public Trigger collectNewsQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10) //设置时间周期单位秒，每10秒执行一次
//                .withIntervalInHours(2) //两个小时执行一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(collectNewsQuartzDetail())
                .withIdentity(COLLECT_NEWS_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }

    /**
     * 定时重置今天用户激活数redis定时任务配置
     * @return
     */
    @Bean
    public JobDetail resetUserTodayActiveQuartzDetail(){
        return JobBuilder.newJob(ResetUserTodayActiveTask.class).withIdentity(RESET_USER_TODAY_ACTIVE_TASK_IDENTITY).storeDurably().build();
    }

    /**
     * 定时重置今天用户激活数redis定时任务触发器
     * @return
     */
    @Bean
    public Trigger resetUserTodayActiveQuartzTrigger(){
        //每天0时触发
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 ? * *");
        return TriggerBuilder.newTrigger().forJob(resetUserTodayActiveQuartzDetail())
                .withIdentity(RESET_USER_TODAY_ACTIVE_TASK_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    /**
     * 定时重置本周用户激活数redis定时任务配置
     * @return
     */
    @Bean
    public JobDetail resetUserThisWeekActiveQuartzDetail(){
        return JobBuilder.newJob(ResetUserThisWeekActiveTask.class).withIdentity(RESET_USER_THISWEEK_ACTIVE_TASK_IDENTITY).storeDurably().build();
    }

    /**
     * 定时重置本周用户激活数redis定时任务触发器
     * @return
     */
    @Bean
    public Trigger resetUserThisWeekActiveQuartzTrigger(){
        //每周一0时整触发
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 ? * MON");
        return TriggerBuilder.newTrigger().forJob(resetUserThisWeekActiveQuartzDetail())
                .withIdentity(RESET_USER_THISWEEK_ACTIVE_TASK_IDENTITY)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
