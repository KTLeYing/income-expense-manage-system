package com.mzl.incomeexpensemanagesystem.service.impl;

import com.mzl.incomeexpensemanagesystem.entity.Admin;
import com.mzl.incomeexpensemanagesystem.mapper.AdminMapper;
import com.mzl.incomeexpensemanagesystem.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
