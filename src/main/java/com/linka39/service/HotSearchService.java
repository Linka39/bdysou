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

    /**
     * 根据条件分页查询热门搜索
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<HotSearch> adminList(Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 获取总记录数
     * @return
     */
    public Long getAdminCount();

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public HotSearch get(Integer id);

    /**
     * 添加或者修改热门搜索
     * @param hotSearch
     */
    public void save(HotSearch hotSearch);

    /**
     * 删除指定id的实体
     * @param id
     */
    public void delete(Integer id);
}
