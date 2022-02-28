package com.mzl.incomeexpensemanagesystem.controller.common;

import com.mzl.incomeexpensemanagesystem.entity.News;
import com.mzl.incomeexpensemanagesystem.response.RetResult;
import com.mzl.incomeexpensemanagesystem.service.NewsService;
import com.mzl.incomeexpensemanagesystem.vo.MemorandumVo;
import com.mzl.incomeexpensemanagesystem.vo.NewsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   NewsController
 * @Description: 新闻控制器
 * @Author: v_ktlema
 * @CreateDate: 2021/12/20 22:09
 * @Version: 1.0
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/news")
@Api(value = "新闻模块接口", tags = "新闻模块接口")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/selectPageNews")
    @ApiOperation(value = "分页模糊查询新闻")
    public RetResult selectPageNews(News news, Integer currentPage, Integer pageSize){
        return newsService.selectPageNews(news, currentPage, pageSize);
    }

    @GetMapping("/lookNewsDetail")
    @ApiOperation(value = "查看新闻详情")
    public RetResult lookNewsDetail(Integer newsId){
        return newsService.lookNewsDetail(newsId);
    }

    @GetMapping("/selectHotNews")
    @ApiOperation(value = "根据浏览数获取热门新闻(TOP10)")
    public RetResult selectHotNews(){
        return newsService.selectHotNews();
    }

    @GetMapping("/collectNewsToRedis")
    @ApiOperation(value = "用户收藏新闻存到Redis")
    public RetResult collectNewsToRedis(Integer newsId){
        return newsService.collectNewsToRedis(newsId);
    }

    @GetMapping("/unCollectNewsFromRedis")
    @ApiOperation(value = "从Redis取消收藏新闻")
    public RetResult unCollectNewsFromRedis(Integer newsId){
        return newsService.unCollectNewsFromRedis(newsId);
    }

    @GetMapping("/selectPageUserCollectNews")
    @ApiOperation(value = "分页模糊查询用户收藏的新闻")
    public RetResult selectPageCollectNews(NewsVo newsVo, Integer currentPage, Integer pageSize){
        return newsService.selectPageCollectNews(newsVo, currentPage, pageSize);
    }

    @GetMapping("/selectUserCollectNewsId")
    @ApiOperation(value = "获取用户收藏新闻的列表【获新闻id】(当前用户收藏了那些新闻)")
    public RetResult selectUserCollectNewsId(){
        return newsService.selectUserCollectNewsId();
    }

    @GetMapping("/selectAllUserCollectThisNews")
    @ApiOperation(value = "获取新闻的收藏列表【获用户id】(有谁收藏了这个新闻)")
    public RetResult selectAllUserCollectThisNews(Integer newsId){
        return newsService.selectAllUserCollectThisNews(newsId);
    }

    @GetMapping("/selectIsCollectNews")
    @ApiOperation(value = "查询当前用户是否收藏了某个新闻")
    public RetResult selectIsCollectNews(Integer newsId){
        return newsService.selectIsCollectNews(newsId);
    }

}
