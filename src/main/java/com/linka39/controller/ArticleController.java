package com.linka39.controller;

import com.linka39.entity.Article;
import com.linka39.service.ArticleService;
import com.linka39.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 提诶子控制器
 * @author linka39
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询最新收录资源
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/{page}")
    public ModelAndView list(@PathVariable(value = "page",required = false)Integer page)throws Exception{
        ModelAndView mav=new ModelAndView();
        List<Article> articleList = articleService.list(page, 20, Sort.Direction.DESC, "includeDate");
        Long count = articleService.getCount();
        mav.addObject("articleList",articleList);
        mav.addObject("pageCode", PageUtil.genPagination("/article/list",count,page,20));
        mav.setViewName("newest");
        mav.addObject("modelName","最新资源收录列表");
        mav.addObject("title","最新资源收录列表");
        return mav;
    }
}
