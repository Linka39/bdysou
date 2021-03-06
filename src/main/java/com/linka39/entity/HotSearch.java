package com.linka39.entity;

import javax.persistence.*;

/**
 * @author java1234_小锋
 * @site www.java1234.com
 * @company Java知识分享网
 * @create 2019-01-15 下午 9:33
 */
@Entity
@Table(name="t_hotSearch")
public class HotSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(length = 100)
    private String name; // 热门搜索词

    private Integer sort; // 热门搜索词  从小到大排序


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
