package com.linka39.service;


import com.linka39.entity.HotSearch;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 热门搜索Service接口
 * @author linka39
 */
public interface HotSearchService {

    /**
     * 查询所有的热门搜索信息
     * @param direction
     * @param properties
     * @return
     */
    //决定升序或降序
    public List<HotSearch> listAll(Sort.Direction direction, String... properties);
}
