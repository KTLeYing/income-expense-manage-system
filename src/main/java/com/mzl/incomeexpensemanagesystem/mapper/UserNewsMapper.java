package com.mzl.incomeexpensemanagesystem.mapper;

import com.mzl.incomeexpensemanagesystem.entity.UserNews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author v_ktlema
 * @since 2022-01-05
 */
public interface UserNewsMapper extends BaseMapper<UserNews> {

    /**
     * 获取用户收藏新闻的列表【获新闻id】(当前用户收藏了那些新闻)
     * @param userId
     * @return
     */
    List<Integer> selectUserCollectNewsId(Integer userId);

    /**
     * 获取新闻的收藏列表【获用户id】(有谁收藏了这个新闻)
     * @param
     * @return
     */
    List<Integer> selectAllUserCollectThisNews(Integer newsId);
}
