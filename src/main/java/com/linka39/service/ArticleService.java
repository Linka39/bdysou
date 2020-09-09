package com.linka39.service;

import com.linka39.entity.Article;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 帖子Service接口
 * @author linka39
 */
public interface ArticleService {

    /**
     * 分页查询所有的帖子信息
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Article> list(Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 获取总记录数
     * @return
     */
    public Long getCount();
}
