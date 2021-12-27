package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userVo
     * @return
     */
    RetResult register(UserVo userVo);

    /**
     * 用户登录
     * @param username
     * @param password
     * @param verifyCode
     * @return
     */
    RetResult userLogin(String username, String password, String verifyCode);

    /**
     * 获取当前用户(根据token)
     * @param request
     * @return
     */
    String getUserId(HttpServletRequest request);

    /**
     * 用户退出登录
     * @return
     */
    RetResult userLogout(HttpServletRequest request);

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    RetResult selectCurrentUser(HttpServletRequest request);

    /**
     * 修改用户密码
     * @param oldPassword
     * @param newPassword
     * @param newPassword1
     * @return
     */
    RetResult updatePassword(String oldPassword, String newPassword, String newPassword1);

    /**
     * 找回密码
     * @param newPassword
     * @param newPassword1
     * @param messageCode
     * @return
     */
    RetResult findBackPassword(String newPassword, String newPassword1, String phone, String messageCode);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    RetResult updateUser(User user);

}
