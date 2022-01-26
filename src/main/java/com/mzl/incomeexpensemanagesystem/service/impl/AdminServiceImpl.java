package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mzl.incomeexpensemanagesystem.entity.Admin;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.mapper.AdminMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.utils.JwtTokenUtil;
import com.mzl.incomeexpensemanagesystem.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mzl.incomeexpensemanagesystem.utils.JwtTokenUtil.EXPIRATION_REMEMBER;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AdminService adminService;

    /**
     * 管理员的token的key前缀
     */
    private static final String TOKEN_KEY_PREFIX = "incomeExpense:admin:token:";

    /**
     * 用户找回密码的短信验证码的key前缀
     */
    private static final String MESSAGE_CODE_KEY_PREFIX = "incomeExpense:messageCode:";


    /**
     * 获取当前管理员(根据token)
     * @param
     * @return
     */
    @Override
    public Integer getAdminId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 取得token
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        tokenHeader = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "").trim();
        log.info("获取当前管理员=====>" + "管理员token为: " + tokenHeader);
        String userId = JwtTokenUtil.getObjectId(tokenHeader);
        log.info("获取当前管理员=====>" + "解析token得到UserId为: " + userId);
        return Integer.parseInt(userId);
    }

    /**
     * 获取当前管理员所有具体信息（辅助用）
     */
    @Override
    public Admin getAdmin(){
        Integer adminId  = getAdminId();
        Admin admin = adminMapper.selectById(adminId);
        return admin;
    }

    /**
     * 获取当前管理员信息
     * @param request
     * @return
     */
    @Override
    public RetResult selectCurrentAdmin(HttpServletRequest request) {
        Admin admin = getAdmin();
        admin.setPassword("");
        return RetResult.success(admin);
    }

    /**
     * 管理员登录
     * @param adminName
     * @param password
     * @return
     */
    @Override
    public RetResult adminLogin(String adminName, String password) {
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        String ip = "";
        try {
            //先用本地地址模拟
            ip = InetAddress.getLocalHost().getHostAddress();
            //获取远程地址(无代理)
//            ip = request.getRemoteAddr();
            //获取远程地址(Nginx代理), 获取nginx转发的实际ip，前端要在请求头配置X-Real-IP的请求头字段（从请求头中获取，如果是在Nginx设置的话要配置一些东西）
//            ip = request.getHeader("X-Real-IP");
            log.info("管理员登录=====>" + "ip: " + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //验证用户名
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_name", adminName);
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (admin == null){
            //用户错误
            return RetResult.fail(RetCodeEnum.USERNAME_ERROR);
        }
        //否则用户名存在，则验证密码
        Boolean pwdTrue = MD5Util.getSaltverifyMD5(password, admin.getPassword());
        if (!pwdTrue){
            //密码错误
            return RetResult.fail(RetCodeEnum.PASSWORD_ERROR);
        }
        //生成用户token并存储
        String token = JwtTokenUtil.createToken(String.valueOf(admin.getAdminId()), admin.getAdminName(), true);
        //设置key的过期时间为一天
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX +admin.getAdminId(), token, EXPIRATION_REMEMBER, TimeUnit.SECONDS);
        //把token返回给前端的Header
        response.setHeader(JwtTokenUtil.TOKEN_HEADER, JwtTokenUtil.TOKEN_PREFIX + token);
        return RetResult.success(RetCodeEnum.LOGIN_SUCCESS);
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @Override
    public RetResult addAdmin(Admin admin) {
        //加密用户密码
        admin.setPassword(MD5Util.getSaltMD5(admin.getPassword()));
        try {
            adminMapper.insert(admin);
            return RetResult.success(RetCodeEnum.ADMIN_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return RetResult.fail(RetCodeEnum.ADMIN_ADD_FAIL);
        }
    }

    /**
     * 修改管理员密码
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
        if (!MD5Util.getSaltverifyMD5(oldPassword, getAdmin().getPassword())){
            return RetResult.fail(RetCodeEnum.OLD_PASSWORD_ERROR);
        }
        //加密密码
        String password1 = MD5Util.getSaltMD5(newPassword);
        //更新密码
        Admin admin = getAdmin();
        admin.setPassword(password1);
        adminMapper.updateById(admin);
        return RetResult.success();
    }

    /**
     * 找回管理员密码(通过短信验证码)
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
            log.info("找回管理员密码=====>" + "ip: " + ip);
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
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Admin admin = adminMapper.selectOne(queryWrapper);
        admin.setPassword(password1);
        adminMapper.updateById(admin);
        return RetResult.success();
    }

    /**
     * 管理员退出登录
     * @param request
     * @return
     */
    @Override
    public RetResult adminLogout(HttpServletRequest request) {
        //清除管理员的token
        //解析出管理员的userid
        String adminId = String.valueOf(adminService.getAdminId());
        redisTemplate.delete(TOKEN_KEY_PREFIX + adminId);
        return RetResult.success(RetCodeEnum.LOGOUT_SUCCESS);
    }

}
