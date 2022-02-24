package com.mzl.incomeexpensemanagesystem.config;

import com.mzl.incomeexpensemanagesystem.interceptor.AdminApiInterceptor;
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
     * 注入用户请求拦截器bean到容器
     * @return
     */
    @Bean
    public ApiInterceptor apiInterceptor(){
        return new ApiInterceptor();
    }

    /**
     * 注入管理员请求拦截器bean到容器
     * @return
     */
    @Bean
    public AdminApiInterceptor adminApiInterceptor(){
        return new AdminApiInterceptor();
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
                        "/admin/**", //不拦截管理员的请求
                        "/system/**",  //不拦截管理员系统的请求
                        "/user/register",
                        "/user/findBackPassword",
                        "/code/**",
                        "/actuator/**",  //放行所有actuator系统监控请求
                        "/assets/**",
                        "/applications/**",
                        "/instances/**",
                        "/dome",
                        "/tone",
                        "/generate/**",
                        "/timbre_audition/**"
                );

        registry.addInterceptor(adminApiInterceptor())
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
                        "/user/**",  //不拦截普通用户的请求
                        "/announcement/**",
                        "/budget/**",
                        "/iEStatistic/**",
                        "/iECategory/**",
                        "/iERecord/**",
                        "/iEStatistic/**",
                        "/memorandum/**",
                        "/news/**",
                        "/userNews/**",
                        "/wishList/**",
                        "/feedback/**",
                        "/admin/adminLogin",
                        "/user/register",
                        "/user/findBackPassword",
                        "/code/**",
                        "/actuator/**",  //放行所有actuator系统监控请求
                        "/assets/**",
                        "/applications/**",
                        "/instances/**",
                        "/dome",
                        "/tone",
                        "/generate/**",
                        "/timbre_audition/**"
                );

    }


}
