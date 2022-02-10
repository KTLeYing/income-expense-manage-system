package com.mzl.incomeexpensemanagesystem.interceptor;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.exception.CustomException;
import com.mzl.incomeexpensemanagesystem.service.AdminService;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.service.impl.UserServiceImpl;
import com.mzl.incomeexpensemanagesystem.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   LoginInterceptor
 * @Description: 对管理员请求进行拦截
 * @Author: v_ktlema
 * @CreateDate: 2021/12/24 10:13
 * @Version: 1.0
 */
@Slf4j
public class AdminApiInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AdminService adminService;

    /**
     * 管理员的token的key前缀
     */
    private static final String TOKEN_KEY_PREFIX = "incomeExpense:admin:token:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("管理员请求拦截器=====>" + "拦截路径:" + request.getRequestURI());
        //获取用户的token
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        log.info("管理员请求拦截器=====>" + "用户token:" + token);
        //如果token为空或token前缀不对，则访问失败
        if (StringUtils.isEmpty(token) || !token.startsWith(JwtTokenUtil.TOKEN_PREFIX)){
            throw new CustomException(RetCodeEnum.TOKEN_ERROR);
        }
        //去掉前缀的token
        String token1 = token.replace(JwtTokenUtil.TOKEN_PREFIX, "").trim();
        //解析出管理员的adminId
        String adminId = String.valueOf(adminService.getAdminId());
        //如果在redis，且redis的token正确的
        if (redisTemplate.hasKey(TOKEN_KEY_PREFIX + adminId) && redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + adminId).equals(token1)){
            //对token进行校验，不抛出异常则token正确
            JwtTokenUtil.getTokenBody(token1);
            //判断用户是否需要延期token， 当redis中的token的TTL小于60秒时，进行延期，防止切断用户的操作，提高用户体验
            if (redisTemplate.getExpire(TOKEN_KEY_PREFIX + adminId, TimeUnit.SECONDS) < 60) {
                redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + adminId, token1, JwtTokenUtil.EXPIRATION_REMEMBER, TimeUnit.SECONDS);
            }
            return true;
        }
        throw new CustomException(RetCodeEnum.TOKEN_EXPIRED);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
