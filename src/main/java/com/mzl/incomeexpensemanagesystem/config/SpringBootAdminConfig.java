package com.mzl.incomeexpensemanagesystem.config;

import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   SpringBootAdminConfig
 * @Description: AdminConfig系统数据监控配置类
 * @Author: mzl
 * @CreateDate: 2022/2/9 23:12
 * @Version: 1.0
 */
@Configuration
public class SpringBootAdminConfig {

    /**
     * 启用httptrace, 跟踪http请求
     * @return
     */
    @Bean
    public InMemoryHttpTraceRepository getInMemoryHttpTrace(){
        return new InMemoryHttpTraceRepository();
    }

}
