package com.linka39.repository;

import com.linka39.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 帖子Repository接口
 * @author linka39
*/
public interface ArticleRepository extends JpaRepository<Article,Integer> , JpaSpecificationExecutor<Article> {
}
