package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.mapper.UserMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.utils.JwtTokenUtil;
import com.mzl.incomeexpensemanagesystem.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.mzl.incomeexpensemanagesystem.utils.JwtTokenUtil.EXPIRATION_REMEMBER;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 用户的token的key前缀
     */
    private static final String TOKEN_KEY_PREFIX = "incomeExpense:user:token:";

    /**
     * 用户登录验证码的key后缀
     */
    private static final String VERIFY_CODE_KEY_SUBFIX = "verifyCode";

    /**
     * 获取当前用户(根据token)
     * @param request
     * @return
     */
    @Override
    public String getUserId(HttpServletRequest request) {
        // 取得token
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        tokenHeader = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "").trim();
        log.info(tokenHeader);
        String userId = JwtTokenUtil.getObjectId(tokenHeader);
        log.info("解析token得到UserId为: " + userId);
        return userId;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public RetResult register(User user) {
        //加密用户密码
        user.setPassword(MD5Util.getSaltMD5(user.getPassword()));
        try {
            userMapper.insert(user);
            return RetResult.success(RetCodeEnum.REGISTER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return RetResult.fail(RetCodeEnum.REGISTER_FAIL);
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param verifyCode
     * @return
     */
    @Override
    public RetResult userLogin(String username, String password, String verifyCode) {
        //先验证验证码
        //从redis中获取存储好的验证码
        //获取远程机器ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        String ip = "";
        try {
            //先用本地地址模拟
            ip = InetAddress.getLocalHost().getHostAddress();
            //获取远程地址(无代理)
//            ip = request.getRemoteAddr();
            //获取远程地址(Nginx代理), 获取nginx转发的实际ip，前端要在请求头配置X-Real-IP的请求头字段（从请求头中获取，如果是在Nginx设置的话要配置一些东西）
//            ip = request.getHeader("X-Real-IP");
            log.info("ip: " + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String realCode = (String) redisTemplate.opsForValue().get(ip + ":" + VERIFY_CODE_KEY_SUBFIX);
        if (StringUtils.isEmpty(realCode)){
            //验证码无效
            return RetResult.fail(RetCodeEnum.VERIFY_CODE_INVALID);
        }
        if (!verifyCode.equalsIgnoreCase(realCode)){
            //验证码错误
            return RetResult.fail(RetCodeEnum.VERIFY_CODE_ERROR);
        }
        //验证用户名
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            //用户错误
            return RetResult.fail(RetCodeEnum.USERNAME_ERROR);
        }
        //否则用户名存在，则验证密码
        Boolean pwdTrue = MD5Util.getSaltverifyMD5(password, user.getPassword());
        if (!pwdTrue){
            //密码错误
            return RetResult.fail(RetCodeEnum.PASSWORD_ERROR);
        }
        //生成用户token并存储
        String token = JwtTokenUtil.createToken(String.valueOf(user.getUserId()), user.getUsername(), true);
        //设置key的过期时间为一天
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + user.getUserId(), token, EXPIRATION_REMEMBER, TimeUnit.SECONDS);
        //把token返回给前端的Header
        response.setHeader(JwtTokenUtil.TOKEN_HEADER, JwtTokenUtil.TOKEN_PREFIX + token);
        //给修改最近一次登录时间
        Date now = new Date();
        user.setCreateTime(now);
        userMapper.updateById(user);
        return RetResult.success(RetCodeEnum.LOGIN_SUCCESS);
    }

    /**
     * 用户退出登录
     * @return
     */
    @Override
    public RetResult userLogout(HttpServletRequest request) {
        //清除用户的token
        //解析出用户的userid
        String userId = userService.getUserId(request);
        redisTemplate.delete(TOKEN_KEY_PREFIX + userId);
        return RetResult.success(RetCodeEnum.LOGOUT_SUCCESS);
    }

}
