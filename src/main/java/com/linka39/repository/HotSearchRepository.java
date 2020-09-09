package com.linka39.repository;
import com.linka39.entity.HotSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 热门搜素Repository接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company Java知识分享网
 * @create 2019-01-17 下午 9:01
 */
public interface HotSearchRepository extends JpaRepository<HotSearch,Integer> , JpaSpecificationExecutor<HotSearch> {
}
