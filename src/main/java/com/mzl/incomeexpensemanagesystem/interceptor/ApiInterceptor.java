package com.mzl.incomeexpensemanagesystem.interceptor;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.exception.CustomException;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.service.impl.UserServiceImpl;
import com.mzl.incomeexpensemanagesystem.utils.JwtTokenUtil;
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
 * @Description: 对用户请求进行拦截
 * @Author: v_ktlema
 * @CreateDate: 2021/12/24 10:13
 * @Version: 1.0
 */
public class ApiInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 用户的token的key前缀
     */
    private static final String TOKEN_KEY_PREFIX = "incomeExpense:user:token:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        //获取用户的token
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        System.out.println(token);
        //如果token为空或token前缀不对，则访问失败
        if (StringUtils.isEmpty(token) || !token.startsWith(JwtTokenUtil.TOKEN_PREFIX)){
            throw new CustomException(RetCodeEnum.TOKEN_ERROR);
        }
        //去掉前缀的token
        String token1 = token.replace(JwtTokenUtil.TOKEN_PREFIX, "").trim();
        //解析出用户的userid
        String userId = String.valueOf(userService.getUserId());
        //如果在redis，且redis的token正确的
        if (redisTemplate.hasKey(TOKEN_KEY_PREFIX + userId) && redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + userId).equals(token1)){
            //对token进行校验，不抛出异常则token正确
            JwtTokenUtil.getTokenBody(token1);
            //判断用户是否需要延期token， 当redis中的token的TTL小于60秒时，进行延期，防止切断用户的操作，提高用户体验
            if (redisTemplate.getExpire(TOKEN_KEY_PREFIX + userId, TimeUnit.SECONDS) < 60) {
                redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + userId, token1, JwtTokenUtil.EXPIRATION_REMEMBER, TimeUnit.SECONDS);
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
