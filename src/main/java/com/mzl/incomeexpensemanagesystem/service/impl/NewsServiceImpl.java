package com.mzl.incomeexpensemanagesystem.service.impl;

import com.mzl.incomeexpensemanagesystem.entity.News;
import com.mzl.incomeexpensemanagesystem.mapper.NewsMapper;
import com.mzl.incomeexpensemanagesystem.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

}
