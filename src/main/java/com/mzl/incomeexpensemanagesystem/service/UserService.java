package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.UserVo;
import io.swagger.models.auth.In;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * @param
     * @return
     */
    Integer getUserId();

    /**
     * 获取当前用户所有具体信息（辅助用）
     */
    User getUser();

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
    RetResult findBackPassword(String newPassword, String newPassword1, String userName, String messageCode);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    RetResult updateUser(User user);

    /**
     * 分页模糊查询用户(管理员)
     * @param user
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageUser(User user, Integer currentPage, Integer pageSize);

    /**
     * 添加用户(管理员)
     * @param user
     * @return
     */
    RetResult addUser(User user);

    /**
     * 删除（注销）用户
     * @param userId
     * @return
     */
    RetResult deleteUser(Integer userId);

    /**
     * 批量删除（注销）用户
     * @param userIds
     * @return
     */
    RetResult deleteBatchUser(Integer[] userIds);

    /**
     * 导出所有用户信息Excel(管理员)
     */
    void exportAllUser(HttpServletResponse response);

    /**
     * 统计今天、本周、历史用户激活数(管理员)
     * @return
     */
    RetResult statisticUserActive();

    /**
     * 近10年用户激活数对比(管理员)
     * @return
     */
    RetResult tenYearUserActive();

    /**
     * 反馈活跃的前10用户(管理员)
     * @return
     */
    RetResult tenFeedbackUser();

    /**
     * 禁用用户(管理员)
     * @param userId
     * @return
     */
    RetResult banUser(Integer userId);

    /**
     * 批量禁用用户(管理员)
     * @param userIds
     * @return
     */
    RetResult banBatchUser(Integer[] userIds);

    /**
     * 解禁用户(管理员)
     * @param userId
     * @return
     */
    RetResult unBanUser(Integer userId);

    /**
     * 批量解禁用户(管理员)
     * @param userIds
     * @return
     */
    RetResult unBanBatchUser(Integer[] userIds);
}
