package com.mzl.incomeexpensemanagesystem.config;

import com.mzl.incomeexpensemanagesystem.interceptor.ApiInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   Web
 * @Description: web配置类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/24 19:57
 * @Version: 1.0
 */
@Configuration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    /**
     * 注入beam到容器
     * @return
     */
    @Bean
    public ApiInterceptor apiInterceptor(){
        return new ApiInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/doc.html",
                        "/swagger-ui.html",
                        "/csrf",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/v2/api-docs",
                        "/error",
                        "/webjars/**",
                        "/**/favicon.ico",
                        "/user/userLogin",
                        "/admin/adminLogin",
                        "/user/register",
                        "/user/findBackPassword",
                        "/code/**",
                        "/dome",
                        "/tone",
                        "/generate/**",
                        "/timbre_audition/**"
                );
    }


}
