package com.linka39.service.impl;

import com.linka39.entity.HotSearch;
import com.linka39.repository.HotSearchRepository;
import com.linka39.service.HotSearchService;
import org.elasticsearch.search.suggest.SortBy;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 热门搜索Service实现类
 * @author  lika39
 */
@Service("hotSearchService")
public class HotSearchServiceImpl implements HotSearchService {

    @Autowired
    private HotSearchRepository hotSearchRepository;

    @Override
    public List<HotSearch> listAll(Sort.Direction direction, String... properties) {
        return hotSearchRepository.findAll(Sort.by(direction,properties));
    }
}
