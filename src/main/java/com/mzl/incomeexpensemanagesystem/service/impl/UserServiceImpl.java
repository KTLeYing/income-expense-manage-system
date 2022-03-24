package com.mzl.incomeexpensemanagesystem.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.User;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.exception.CustomException;
import com.mzl.incomeexpensemanagesystem.mapper.UserMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.utils.JwtTokenUtil;
import com.mzl.incomeexpensemanagesystem.utils.MD5Util;
import com.mzl.incomeexpensemanagesystem.excel.vo.UserExcelVo;
import com.mzl.incomeexpensemanagesystem.vo.UserStatisticVo;
import com.mzl.incomeexpensemanagesystem.vo.UserVo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
     * 用户今天激活数的key
     */
    private static final String USER_ACTIVE_TODAY_KEY = "incomeExpense:userActive:today";

    /**
     * 用户本周激活数的key
     */
    private static final String USER_ACTIVE_THISWEEK_KEY = "incomeExpense:userActive:thisWeek";

    /**
     * 用户历史激活数的key
     */
    private static final String USER_ACTIVE_HISTORY_KEY = "incomeExpense:userActive:history";

    /**
     * 每个工作表sheet存储的记录数 100W(1000000)
     */
    private static final Integer PER_SHEET_ROW_COUNT = 1000000;

    /**
     * 每次分页查询后向EXCEL写入的记录数(查询每页数据大小) 20W（200000）
     */
    private static final Integer PER_WRITE_ROW_COUNT = 200000;

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
        log.info("获取当前用户=====>" + "用户token为: " + tokenHeader);
        String userId = JwtTokenUtil.getObjectId(tokenHeader);
        log.info("获取当前用户=====>" + "解析token得到UserId为: " + userId);
        return Integer.parseInt(userId);
    }

    /**
     * 获取当前用户所有具体信息（辅助用）
     */
    @Override
    public User getUser(){
        Integer userId  = getUserId();
        User user = userMapper.selectById(userId);
        if (user == null){
            //当前用户为空，并抛出异常
            throw new CustomException(RetCodeEnum.USER_NULL);
        }
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
            log.info("用户注册=====>" + "ip: " + ip);
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
        //默认为未禁用
        user.setBanned(1);
        //默认未删除
        user.setDeleted(true);
        try {
            userMapper.insert(user);

            //更新今天、本周、历史用户激活数Redis，管理员用于统计
            //今天
            if (!redisTemplate.hasKey(USER_ACTIVE_TODAY_KEY)){
                redisTemplate.opsForValue().set(USER_ACTIVE_TODAY_KEY, 1);
            }else {
                redisTemplate.opsForValue().increment(USER_ACTIVE_TODAY_KEY, 1);
            }
            //本周
            if (!redisTemplate.hasKey(USER_ACTIVE_THISWEEK_KEY)){
                redisTemplate.opsForValue().set(USER_ACTIVE_THISWEEK_KEY, 1);
            }else {
                redisTemplate.opsForValue().increment(USER_ACTIVE_THISWEEK_KEY, 1);
            }
            //历史
            if (!redisTemplate.hasKey(USER_ACTIVE_HISTORY_KEY)){
                redisTemplate.opsForValue().set(USER_ACTIVE_HISTORY_KEY, 1);
            }else {
                redisTemplate.opsForValue().increment(USER_ACTIVE_HISTORY_KEY, 1);
            }

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
            log.info("用户登录=====>" + "ip: " + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String realCode = (String) redisTemplate.opsForValue().get(GRAPHIC_CODE_KEY_PREFIX + ip);
        log.info("用户登录=====>" + "realCode: " + realCode);
        if (StringUtils.isEmpty(realCode) || !realCode.equalsIgnoreCase(verifyCode)){
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
        //否则用户名存在，则判断用户是否被禁用
        if (user.getBanned() == 2){
            //用户已被禁用
            return RetResult.fail(RetCodeEnum.USERNAME_BANNED);
        }
        //否则用户名存在且未被禁用，则验证密码
        Boolean pwdTrue = MD5Util.getSaltverifyMD5(password, user.getPassword());
        if (!pwdTrue){
            //密码错误
            return RetResult.fail(RetCodeEnum.PASSWORD_ERROR);
        }
        //生成用户token并存储
        String token = JwtTokenUtil.createToken(String.valueOf(user.getUserId()), user.getUsername(), true);
        log.info("用户登录=====>" + "生成的用户token: " + token);
        //设置key的过期时间为一天
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + user.getUserId(), token, EXPIRATION_REMEMBER, TimeUnit.SECONDS);
        //把token返回给前端的Header
        response.addHeader(JwtTokenUtil.TOKEN_HEADER, JwtTokenUtil.TOKEN_PREFIX + token);
        response.setHeader("Access-Control-Expose-Headers", JwtTokenUtil.TOKEN_HEADER);
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
    public RetResult findBackPassword(String newPassword, String newPassword1, String userName, String messageCode) {
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
            log.info("找回密码=====>" + "ip: " + ip);
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
        queryWrapper.eq("username", userName);
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
        if (user1 == null){
            //当前用户为空，并抛出异常
            throw new CustomException(RetCodeEnum.USER_NULL);
        }
        user.setPassword(user1.getPassword());
        user.setCreateTime(user1.getCreateTime());
        user.setLastLoginTime(user1.getLastLoginTime());
        user.setBanned(user1.getBanned());
        user.setDeleted(true);
        userMapper.updateById(user);
        return RetResult.success();
    }

    /**
     * 分页模糊查询用户(管理员)
     * @param user
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageUser(User user, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(user.getUsername()), "username", user.getUsername());
        queryWrapper.like(!StringUtils.isEmpty(user.getName()), "name", user.getName());
        queryWrapper.eq(user.getSex() != null && user.getSex() != 0, "sex", user.getSex());
        queryWrapper.eq(user.getBanned() != null && user.getBanned() != 0, "banned", user.getBanned());
        IPage<User> page = new Page<>(currentPage, pageSize);
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        return RetResult.success(userIPage);
    }

    /**
     * 添加用户(管理员)
     * @param user
     * @return
     */
    @Override
    public RetResult addUser(User user) {
        //加密用户密码
        Date now = new Date();
        user.setCreateTime(now);
        user.setLastLoginTime(now);
        user.setPassword(MD5Util.getSaltMD5(user.getPassword()));
        //默认为未禁用
        user.setBanned(1);
        //默认未删除
        user.setDeleted(true);
        try {
            userMapper.insert(user);
            return RetResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return RetResult.fail();
        }
    }

    /**
     * 删除（注销）用户
     * @param userId
     * @return
     */
    @Override
    public RetResult deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
        return RetResult.success();
    }

    /**
     * 批量删除（注销）用户
     * @param userIds
     * @return
     */
    @Override
    public RetResult deleteBatchUser(Integer[] userIds) {
        List<Integer> idList = Arrays.stream(userIds).collect(Collectors.toList());
        userMapper.deleteBatchIds(idList);
        return RetResult.success();
    }

    /**
     * 导出所有用户信息Excel(管理员)
     */
    @Override
    public void exportAllUser(HttpServletResponse response) {
        String fileName = "all_user_data";
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        Integer userId = userService.getUserId();

        UserExcelVo UserExcelVo = new UserExcelVo();
        //总记录数
        Integer totalRowCount = userMapper.selectCount(null);
        log.info("导出所有用户信息Excel(管理员)=====>" + "总记录数：" + totalRowCount);
        //总工作表(sheet)数
        Integer sheetCount = totalRowCount % PER_SHEET_ROW_COUNT == 0 ? (totalRowCount / PER_SHEET_ROW_COUNT) : (totalRowCount / PER_SHEET_ROW_COUNT + 1);
        log.info("导出所有用户信息Excel(管理员)=====>" + "总工作表(sheet)数：" + sheetCount);

        ServletOutputStream out = null;
        ExcelWriter excelWriter = null;

        try {
            out = response.getOutputStream();
            excelWriter = EasyExcel.write(out).build();
            WriteSheet writeSheet = null;
            //存储总数据的list
            List<UserExcelVo> userExcelVoList = new ArrayList<>();
            //分页参数
            Page<UserExcelVo> userExcelVoPage = new Page<>();
            //设置分页参数每页大小
            userExcelVoPage.setSize(PER_WRITE_ROW_COUNT);
            if (sheetCount > 1){
                //数据量大于100w
                //每个工作表(sheet)的查询次数
                Integer perSheetWriteCount = PER_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT;
                //最后一个总工作表(sheet)的查询次数
                Integer lastSheetWriteCount = totalRowCount % PER_SHEET_ROW_COUNT == 0 ?
                        perSheetWriteCount :
                        (totalRowCount % PER_SHEET_ROW_COUNT % PER_WRITE_ROW_COUNT == 0 ? totalRowCount % PER_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT : (totalRowCount % PER_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT + 1));
                for (int i = 0; i < sheetCount; i++) {
                    for (int j = 0; j < (i != sheetCount? perSheetWriteCount : lastSheetWriteCount); j++) {
                        Integer current = j + 1 + perSheetWriteCount * i;
                        //设置分页参数当前页数
                        userExcelVoPage.setCurrent(current);
                        log.info("导出所有用户信息Excel(管理员)=====>" + "当前页数：" + current);
                        //收支记录的分页查询
                        userExcelVoPage = userMapper.selectPageUserExcel(userExcelVoPage);
                        log.info("导出所有用户信息Excel(管理员)=====>" + "收支记录分页数据(多个sheet)：" + userExcelVoPage);
                        userExcelVoList = userExcelVoPage.getRecords();

                        //将这个工作表结果写入excel
                        writeSheet = EasyExcel.writerSheet(i, "收支记录" + (i + 1)).head(UserExcelVo.class).build();
                        excelWriter.write(userExcelVoList, writeSheet);
                        userExcelVoList.clear();
                    }
                }

            } else {
                //只有一个工作表(sheet)的数据
                Integer perSheetWriteCount = PER_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT;
                //查询次数
                Integer writeCount = totalRowCount % PER_WRITE_ROW_COUNT == 0 ? (totalRowCount / PER_WRITE_ROW_COUNT) : (totalRowCount / PER_WRITE_ROW_COUNT + 1);
                for (int i = 1; i <= writeCount; i++) {
                    //设置分页参数当前页数
                    userExcelVoPage.setCurrent(i);
                    log.info("导出所有用户信息Excel(管理员)=====>" + "当前页数：" + i);
                    //收支记录的分页查询
                    userExcelVoPage = userMapper.selectPageUserExcel(userExcelVoPage);
                    log.info("导出所有用户信息Excel(管理员)=====>" + "收支记录分页数据(1个sheet)：" + userExcelVoPage);
                    userExcelVoList = userExcelVoPage.getRecords();

                    //将这个工作表结果写入excel
                    writeSheet = EasyExcel.writerSheet(1, "收支记录").head(UserExcelVo.class).build();
                    excelWriter.write(userExcelVoList, writeSheet);
                    userExcelVoList.clear();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 统计今天、本周、历史用户激活数(管理员)
     * @return
     */
    @Override
    public RetResult statisticUserActive() {
        HashMap<String, Object> statisticResult = new HashMap<>();
        Integer todayActive = (Integer) redisTemplate.opsForValue().get(USER_ACTIVE_TODAY_KEY);
        Integer thisWeekActive = (Integer) redisTemplate.opsForValue().get(USER_ACTIVE_THISWEEK_KEY);
        Integer historyActive = (Integer) redisTemplate.opsForValue().get(USER_ACTIVE_HISTORY_KEY);
        log.info("统计今天、本周、历史用户激活数(管理员)=====>" + "统计结果:" + todayActive + "——" + thisWeekActive + "——" + historyActive);
        statisticResult.put("todayActive", todayActive);
        statisticResult.put("thisWeekActive", thisWeekActive);
        statisticResult.put("historyActive", historyActive);
        return RetResult.success(statisticResult);
    }

    /**
     * 近10年用户激活数对比(管理员)
     * @return
     */
    @Override
    public RetResult tenYearUserActive() {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        //创建日历对象
        Calendar cal = Calendar.getInstance();
        //将时间日期数据传入日历对象
        cal.setTime(now);
        //设置月份加10年 【年、月、日相加操作均类似】
        cal.add(Calendar.YEAR, -10);
        Date now1 = cal.getTime();
        String fromYear = sf.format(now1);
        String toYear = sf.format(now);
        List<UserStatisticVo> userActiveList = userMapper.tenYearUserActive(fromYear, toYear);
        return RetResult.success(userActiveList);
    }

    /**
     * 反馈活跃的前10用户(管理员)
     * @return
     */
    @Override
    public RetResult tenFeedbackUser() {
        List<UserStatisticVo> userPostList = userMapper.tenFeedbackUser();
        return RetResult.success(userPostList);
    }

    /**
     * 禁用用户(管理员)
     * @param userId
     * @return
     */
    @Override
    public RetResult banUser(Integer userId) {
        userMapper.banUser(userId);
        return RetResult.success();
    }

    /**
     * 批量禁用用户(管理员)
     * @param userIds
     * @return
     */
    @Override
    public RetResult banBatchUser(Integer[] userIds) {
        List<Integer> userIdsList = Arrays.stream(userIds).collect(Collectors.toList());
        userMapper.banBatchUser(userIdsList);
        return RetResult.success();
    }

    /**
     * 解禁用户(管理员)
     * @param userId
     * @return
     */
    @Override
    public RetResult unBanUser(Integer userId) {
        userMapper.unBanUser(userId);
        return RetResult.success();
    }

    /**
     * 批量解禁用户(管理员)
     * @param userIds
     * @return
     */
    @Override
    public RetResult unBanBatchUser(Integer[] userIds) {
        List<Integer> userIdsList = Arrays.stream(userIds).collect(Collectors.toList());
        userMapper.unBanBatchUser(userIdsList);
        return RetResult.success();
    }

}
