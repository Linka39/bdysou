package com.linka39.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根目录Controller
 * @author linka39
 */
@Controller
public class IndexController {

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
}
