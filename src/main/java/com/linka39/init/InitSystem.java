package com.linka39.init;

import com.linka39.entity.HotSearch;
import com.linka39.service.HotSearchService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * 初始化加载数据
 * @author linka39
 */
@Component//加上component才可以扫描到
public class InitSystem implements ApplicationContextAware, ServletContextListener {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void loadData(ServletContext application){
        // 获取applicationContext上下文的域来初始化接口并强转
        HotSearchService hotSearchService= (HotSearchService)applicationContext.getBean("hotSearchService");
        List<HotSearch> hotSearchList = hotSearchService.listAll(Sort.Direction.ASC, "sort");
        application.setAttribute("hotSearchList",hotSearchList);
    }

    @Override
    // ServletContextListener 是整个Web容器的声明周期
    public void contextInitialized(ServletContextEvent sce) {
        loadData(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
