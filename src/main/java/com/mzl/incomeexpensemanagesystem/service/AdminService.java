package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.response.RetResult;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface AdminService extends IService<Admin> {

    /**
     * 获取当前管理员(根据token)
     * @param
     * @return
     */
    Integer getAdminId();

    /**
     * 获取当前管理员所有具体信息（辅助用）
     */
    Admin getAdmin();

    /**
     * 获取当前管理员信息
     * @param request
     * @return
     */
    RetResult selectCurrentAdmin(HttpServletRequest request);

    /**
     * 管理员登录
     * @param adminName
     * @param password
     * @return
     */
    RetResult adminLogin(String adminName, String password);

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    RetResult addAdmin(Admin admin);

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
     * @param phone
     * @param messageCode
     * @return
     */
    RetResult findBackPassword(String newPassword, String newPassword1, String phone, String messageCode);

    /**
     * 管理员退出登录
     * @param request
     * @return
     */
    RetResult adminLogout(HttpServletRequest request);

}
