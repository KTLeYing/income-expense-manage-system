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
import com.mzl.incomeexpensemanagesystem.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
import java.util.Objects;
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
     * 用户登录的图文验证码的key前缀
     */
    private static final String GRAPHIC_CODE_KEY_PREFIX = "incomeExpense:graphicCode:";

    /**
     * 用户找回密码的短信验证码的key前缀
     */
    private static final String MESSAGE_CODE_KEY_PREFIX = "incomeExpense:messageCode:";

    /**
     * 用户注册的邮箱验证码的key前缀
     */
    private static final String EMAIL_CODE_KEY_PREFIX = "incomeExpense:emailCode:";


    /**
     * 获取当前用户(根据token)
     * @param
     * @return
     */
    @Override
    public Integer getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 取得token
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        tokenHeader = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "").trim();
        log.info(tokenHeader);
        String userId = JwtTokenUtil.getObjectId(tokenHeader);
        log.info("解析token得到UserId为: " + userId);
        return Integer.parseInt(userId);
    }

    /**
     * 获取当前用户所有具体信息（辅助用）
     */
    @Override
    public User getUser(){
        Integer userId  = getUserId();
        User user = userMapper.selectById(userId);
        return user;
    }

    /**
     * 用户注册
     * @param userVo
     * @return
     */
    @Override
    public RetResult register(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        //判断邮箱验证码是否正确
        //获取远程机器ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
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
        //从redis获取邮箱验证码
        String realCode = (String) redisTemplate.opsForValue().get(EMAIL_CODE_KEY_PREFIX + ip);
        if (StringUtils.isEmpty(realCode) || !realCode.equalsIgnoreCase(userVo.getEmailCode())){
            //邮箱验证码错误
            return RetResult.fail(RetCodeEnum.EMAIL_CODE_ERROR);
        }
        //查询用户名或手机号或邮箱已存在(用户名、手机号、邮箱只能唯一)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername()).or().eq("email", user.getEmail()).or().eq("phone", user.getPhone());
        User userExist = userMapper.selectOne(queryWrapper);
        if (userExist != null){
            //用户名或手机号或邮箱已存在
            return RetResult.fail(RetCodeEnum.USERNAME_EMAIL_PHONE_EXIST);
        }
        //加密用户密码
        Date now = new Date();
        user.setCreateTime(now);
        user.setLastLoginTime(now);
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
        String realCode = (String) redisTemplate.opsForValue().get(GRAPHIC_CODE_KEY_PREFIX + ip);
        if (StringUtils.isEmpty(realCode) || !verifyCode.equalsIgnoreCase(realCode)){
            //图文验证码无效或错误
            return RetResult.fail(RetCodeEnum.GRAPHIC_CODE_ERROR);
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
        user.setLastLoginTime(now);
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
        String userId = String.valueOf(userService.getUserId());
        redisTemplate.delete(TOKEN_KEY_PREFIX + userId);
        return RetResult.success(RetCodeEnum.LOGOUT_SUCCESS);
    }

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    @Override
    public RetResult selectCurrentUser(HttpServletRequest request) {
        User user = getUser();
        user.setPassword("");
        return RetResult.success(user);
    }

    /**
     * 修改用户密码
     * @param oldPassword
     * @param newPassword
     * @param newPassword1
     * @return
     */
    @Override
    public RetResult updatePassword(String oldPassword, String newPassword, String newPassword1) {
        //判断两次新密码是否相同
        if (!Objects.equals(newPassword, newPassword1)){
            return RetResult.fail(RetCodeEnum.TWO_NEW_PASSWORD_NOT_SAME);
        }
        //判断旧密码正确不
        if (!MD5Util.getSaltverifyMD5(oldPassword, getUser().getPassword())){
            return RetResult.fail(RetCodeEnum.OLD_PASSWORD_ERROR);
        }
        //加密密码
        String password1 = MD5Util.getSaltMD5(newPassword);
        //更新密码
        User user = getUser();
        user.setPassword(password1);
        userMapper.updateById(user);
        return RetResult.success();
    }

    /**
     * 找回密码(通过短信验证码)
     * @param newPassword
     * @param newPassword1
     * @param messageCode
     * @return
     */
    @Override
    public RetResult findBackPassword(String newPassword, String newPassword1, String phone, String messageCode) {
        //判断两次新密码是否相同
        if (!Objects.equals(newPassword, newPassword1)){
            return RetResult.fail(RetCodeEnum.TWO_NEW_PASSWORD_NOT_SAME);
        }
        //判断短信验证码
        //获取远程机器ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
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
        //从redis中获取短信验证码
        Integer realMessageCode = (Integer) redisTemplate.opsForValue().get(MESSAGE_CODE_KEY_PREFIX + ip);
        if (Objects.isNull(realMessageCode) || !String.valueOf(realMessageCode).equalsIgnoreCase(messageCode)){
            //短信验证码错误
            return RetResult.fail(RetCodeEnum.MESSAGE_CODE_ERROR);
        }
        //加密密码
        String password1 = MD5Util.getSaltMD5(newPassword);
        //更新密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(queryWrapper);
        user.setPassword(password1);
        userMapper.updateById(user);
        return RetResult.success();
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public RetResult updateUser(User user) {
        User user1 = userMapper.selectById(user.getUserId());
        user.setPassword(user1.getPassword());
        user.setCreateTime(user1.getCreateTime());
        user.setLastLoginTime(user1.getLastLoginTime());
        userMapper.updateById(user);
        return RetResult.success();
    }

}
