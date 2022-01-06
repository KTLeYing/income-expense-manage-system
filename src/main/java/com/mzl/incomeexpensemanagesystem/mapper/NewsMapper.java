package com.mzl.incomeexpensemanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzl.incomeexpensemanagesystem.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzl.incomeexpensemanagesystem.vo.NewsVo;

import java.util.List;

/**
 * <p>
 * 新闻表 Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 新闻浏览数+1
     * @param id
     */
    void updateVisitCount(Integer id);

    /**
     * 分页模糊查询用户收藏新闻的列表(当前用户收藏了那些新闻)
     * @param page
     * @param newsVo
     * @return
     */
    IPage<NewsVo> selectPageCollectNews(IPage<NewsVo> page, NewsVo newsVo);

    /**
     * 根据浏览数获取热门新闻(TOP10)
     * @return
     */
    List<News> selectHotNews();

}
