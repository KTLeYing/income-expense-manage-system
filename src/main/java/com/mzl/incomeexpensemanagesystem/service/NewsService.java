package com.mzl.incomeexpensemanagesystem.service;

import com.mzl.incomeexpensemanagesystem.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.incomeexpensemanagesystem.entity.UserNews;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.vo.NewsVo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 新闻表 服务类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
public interface NewsService extends IService<News> {

    /**
     * 分页模糊查询新闻
     * @param news
     * @param currentPage
     * @param pageSize
     * @return
     */
    RetResult selectPageNews(News news, Integer currentPage, Integer pageSize);

    /**
     * 查看新闻详情
     * @param newsId
     * @return
     */
    RetResult lookNewsDetail(Integer newsId);

    /**
     * 根据浏览数获取热门新闻(TOP10)
     * @return
     */
    RetResult selectHotNews();

    /**
     * 用户收藏新闻
     * @param  newsId
     * @return
     */
    RetResult collectNewsToRedis(Integer  newsId);

    /**
     * 从Redis取消收藏新闻
     * @param newsId
     * @return
     */
    RetResult unCollectNewsFromRedis(Integer newsId);

    /**
     * 同步redis用户收藏新闻数据到数据库
     */
    void transCollectNewsFromRedisToDb();

    /**
     * 从redis获取用户收藏新闻数据
     * @return
     */
    List<UserNews> getCollectNewsFromRedis();

    /**
     * 同步redis新闻收藏数到数据库
     */
    void transCollectNewsCountFromRedisToDb();

    /**
     * 从redis获取新闻收藏数
     * @return
     */
    List<News> getCollectNewsCountFromRedis();

    /**
     * 分页模糊查询用户收藏的新闻
     * @return
     */
    RetResult selectPageCollectNews(NewsVo newsVo, Integer currentPage, Integer pageSize);

    /**
     * 获取用户收藏新闻的列表【获新闻id】(当前用户收藏了那些新闻)
     * @return
     */
    RetResult selectUserCollectNewsId();

    /**
     * 获取新闻的收藏列表【获用户id】(有谁收藏了这个新闻)
     * @return
     */
    RetResult selectAllUserCollectThisNews(Integer newsId);

    /**
     * 查询当前用户是否收藏了某个新闻
     * @param newsId
     * @return
     */
    RetResult selectIsCollectNews(Integer newsId);

}
