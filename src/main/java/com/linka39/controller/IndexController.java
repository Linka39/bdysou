package com.linka39.controller;

import com.linka39.entity.es.ArticleInfo;
import com.linka39.service.ArticleService;
import com.linka39.util.PageUtil;
import com.linka39.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 根目录Controller
 * @author linka39
 */
@Controller
public class IndexController {
    @Autowired
    private ArticleService articleService;

    /**
     * 根目录请求
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("index");
        mav.addObject("title","首页");
        return mav;
    }

    /**
     * 登录请求
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    /**
     * 进入后台管理请求
     * @return
     */
    @RequestMapping("/admin")
    public String toAdmin(){
        return "/admin/main";
    }

    /**
     * 去往欢迎画面
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin/towelcom")
    public String towelcom(){
        return "/admin/welcom";
    }
    /**
     * 分词查询
     * @param q
     * @return
     * @throws Exception
     */
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(value="q",required = false)String q,
                               @RequestParam(value = "page",required = false)String page)throws Exception{
        ModelAndView mav=new ModelAndView();
        if(StringUtil.isEmpty(q)){
            mav.setViewName("index");
            mav.addObject("title","首页");
            return mav;
        }
        int pageSize=10;
        if(StringUtil.isEmpty(page)){
            page="1";
        }
        mav.addObject("q",q);
        List<ArticleInfo> articleInfoList = articleService.search(Integer.parseInt(page), pageSize, q);
        Long total = articleService.searchCount(q);
        mav.addObject("articleInfoList",articleInfoList);
        mav.addObject("total",total);
        mav.addObject("title",q);
        mav.addObject("modelName",q+" - 搜索结果");
        mav.addObject("pageCode", PageUtil.genSearchPagination("/search",total,Integer.parseInt(page),pageSize,q));
        mav.setViewName("result");

        return mav;
    }
}
