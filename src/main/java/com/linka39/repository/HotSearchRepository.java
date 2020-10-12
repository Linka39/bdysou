package com.linka39.repository;
import com.linka39.entity.HotSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 热门搜素Repository接口
 * @author linka39
 */
public interface HotSearchRepository extends JpaRepository<HotSearch,Integer> , JpaSpecificationExecutor<HotSearch> {
}
