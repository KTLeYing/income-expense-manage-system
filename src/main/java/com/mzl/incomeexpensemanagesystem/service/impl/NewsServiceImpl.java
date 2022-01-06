package com.mzl.incomeexpensemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzl.incomeexpensemanagesystem.entity.News;
import com.mzl.incomeexpensemanagesystem.entity.UserNews;
import com.mzl.incomeexpensemanagesystem.enums.CollectStatusEnum;
import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.mapper.NewsMapper;
import com.mzl.incomeexpensemanagesystem.mapper.UserMapper;
import com.mzl.incomeexpensemanagesystem.mapper.UserNewsMapper;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl.incomeexpensemanagesystem.service.UserService;
import com.mzl.incomeexpensemanagesystem.vo.NewsVo;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 新闻表 服务实现类
 * </p>
 *
 * @author v_ktlema
 * @since 2021-12-22
 */
@Service
@Transactional
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UserNewsMapper userNewsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户收藏新闻redis的key的hash
     */
    private static final String USER_COLLECT_NEW_KEY = "incomeExpense:newsCollect:userCollect";

    /**
     * 新闻被收藏数redis的key的hash
     */
    private static final String NEW_COLLECT_COUNT_KEY = "incomeExpense:newsCollect:count";

    /**
     * 分页模糊查询新闻
     * @param news
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RetResult selectPageNews(News news, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(news.getTitle()), "title", news.getTitle());
        queryWrapper.like(!StringUtils.isEmpty(news.getKeyword()), "keyword", news.getKeyword());
        queryWrapper.like(!StringUtils.isEmpty(news.getContent()), "content", news.getContent());
        queryWrapper.like(!StringUtils.isEmpty(news.getAuthor()), "author", news.getAuthor());
        queryWrapper.eq(news.getType() != null && news.getType() != 0, "type", news.getType());
        IPage<News> page = new Page<>(currentPage, pageSize);
        IPage<News> newsIPage = newsMapper.selectPage(page, queryWrapper);
        return RetResult.success(newsIPage);
    }

    /**
     * 查看新闻详情
     * @param newsId
     * @return
     */
    @Override
    public RetResult lookNewsDetail(Integer newsId) {
        //新闻浏览数+1
        newsMapper.updateVisitCount(newsId);
        //查询某个新闻
        News news = newsMapper.selectById(newsId);
        return RetResult.success(news);
    }

    /**
     * 根据浏览数获取热门新闻(TOP10)
     * @return
     */
    @Override
    public RetResult selectHotNews() {
        List<News> newsList = newsMapper.selectHotNews();
        return RetResult.success(newsList);
    }

    /**
     * 用户收藏新闻到redis
     * @param  newsId
     * @return
     */
    @Override
    public RetResult collectNewsToRedis(Integer newsId) {
        Integer userId = userService.getUserId();
        //添加新闻到用户的收藏记录到redis(可能或替换)
        redisTemplate.opsForHash().put(USER_COLLECT_NEW_KEY, generateUserCollectHashKey(userId, newsId), CollectStatusEnum.COLLECT.getCode());
        //新闻的redis的收藏数+1
        redisTemplate.opsForHash().increment(NEW_COLLECT_COUNT_KEY, String.valueOf(newsId), 1);
        return RetResult.success();
    }

    /**
     * 从Redis取消收藏新闻
     * @param newsId
     * @return
     */
    @Override
    public RetResult unCollectNewsFromRedis(Integer newsId) {
        Integer userId = userService.getUserId();
        //添加新闻到用户的收藏记录到redis(可能或替换)
        redisTemplate.opsForHash().put(USER_COLLECT_NEW_KEY, generateUserCollectHashKey(userId, newsId), CollectStatusEnum.UN_COLLECT.getCode());
        //新闻的redis的收藏数-1
        redisTemplate.opsForHash().increment(NEW_COLLECT_COUNT_KEY, String.valueOf(newsId), -1);
        return RetResult.success();
    }

    /**
     * 同步redis用户收藏新闻数据到数据库
     */
    @Override
    public void transCollectNewsFromRedisToDb() {
        //从redis获取用户收藏新闻数据
        List<UserNews> userNewsList = getCollectNewsFromRedis();
        //遍历list
        for (UserNews userNews : userNewsList) {
            //判断是否存在该记录
            QueryWrapper<UserNews> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userNews.getUserId());
            queryWrapper.eq("news_id", userNews.getNewsId());
            UserNews userNews1 = userNewsMapper.selectOne(queryWrapper);
            if (userNews1 == null){
                //如果不存在，则插入
                userNewsMapper.insert(userNews);
            }else {
                //如果存在，则更新
                userNews1.setStatus(userNews.getStatus());
                userNewsMapper.updateById(userNews1);
            }
        }
    }

    /**
     * 从redis获取用户收藏新闻数据
     * @return
     */
    @Override
    public List<UserNews> getCollectNewsFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(USER_COLLECT_NEW_KEY, ScanOptions.NONE);
        List<UserNews> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 UserId，newsId
            String[] split = key.split("::");
            String userId = split[0];
            String newsId = split[1];
            Integer value = (Integer) entry.getValue();
            //组装成 UserNews 对象
            UserNews userNews = new UserNews();
            userNews.setUserId(Integer.parseInt(userId));
            userNews.setNewsId(Integer.parseInt(newsId));
            userNews.setStatus(value == 1? true : false);
            list.add(userNews);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(USER_COLLECT_NEW_KEY, key);
        }
        return list;
    }

    /**
     * 同步redis新闻收藏数到数据库
     */
    @Override
    public void transCollectNewsCountFromRedisToDb() {
        //从redis获取新闻收藏数
        List<News> newsList = getCollectNewsCountFromRedis();
        //遍历list
        for (News news : newsList) {
            //判断新闻是否存在
            News news1 = newsMapper.selectById(news.getNewsId());
            if (news1 != null){
                //新闻存在则更新收藏数
                news1.setCollectCount(news1.getCollectCount() + news.getCollectCount());
                newsMapper.updateById(news1);
            }
        }
    }

    /**
     * 从redis获取新闻收藏数
     * @return
     */
    @Override
    public List<News> getCollectNewsCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(NEW_COLLECT_COUNT_KEY, ScanOptions.NONE);
        List<News> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer value = (Integer) entry.getValue();
            //组装成 News 对象
            News news = new News();
            news.setNewsId(Integer.parseInt(key));
            news.setCollectCount(Long.valueOf(value));
            list.add(news);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(NEW_COLLECT_COUNT_KEY, key);
        }
        return list;
    }

    /**
     * 分页模糊查询用户收藏的新闻
     * @return
     */
    @Override
    public RetResult selectPageCollectNews(NewsVo newsVo, Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage == 0){
            currentPage = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        Integer userId = userService.getUserId();
        IPage<NewsVo> page = new Page<>(currentPage, pageSize);
        newsVo.setUserId(userId);
        IPage<NewsVo> newsIPage = newsMapper.selectPageCollectNews(page, newsVo);
        return RetResult.success(newsIPage);
    }

    /**
     * 获取用户收藏新闻的列表【获新闻id】(当前用户收藏了那些新闻)
     * @return
     */
    @Override
    public RetResult selectUserCollectNewsId() {
        Integer userId = userService.getUserId();
        List<Integer> newsIdList = userNewsMapper.selectUserCollectNewsId(userId);
        return RetResult.success(newsIdList);
    }

    /**
     * 获取新闻的收藏列表【获用户id】(有谁收藏了这个新闻)
     * @return
     */
    @Override
    public RetResult selectAllUserCollectThisNews(Integer newsId) {
        List<Integer> userIdList = userNewsMapper.selectAllUserCollectThisNews(newsId);
        return RetResult.success(userIdList);
    }

    /**
     * 查询当前用户是否收藏了某个新闻
     * @param newsId
     * @return
     */
    @Override
    public RetResult selectIsCollectNews(Integer newsId) {
        Integer userId = userService.getUserId();
        QueryWrapper<UserNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("news_id", newsId);
        queryWrapper.eq("status", CollectStatusEnum.COLLECT.getCode());
        UserNews userNews = userNewsMapper.selectOne(queryWrapper);
        if (userNews != null){
            return RetResult.success(RetCodeEnum.USER_COLLECT_NEWS);
        }
        return RetResult.success(RetCodeEnum.USER_UN_COLLECTED_NEWS);
    }

    /**
     * 通过用户id和新闻id生成用户收藏新闻hash的键key
     * @param userId
     * @param newsId
     * @return
     */
    public String generateUserCollectHashKey(Integer userId, Integer newsId){
        return userId + "::" + newsId;
    }


}
