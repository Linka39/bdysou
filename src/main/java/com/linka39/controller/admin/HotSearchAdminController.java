package com.linka39.controller.admin;
import com.linka39.entity.HotSearch;
import com.linka39.init.InitSystem;
import com.linka39.service.HotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理-热门搜索控制器
 * @author linka39
 */
@RestController
@RequestMapping("/admin/hotSearch")
public class HotSearchAdminController {

    @Autowired
    private HotSearchService hotSearchService;
    @Autowired
    private InitSystem initSystem;

    /**
     * 分页查询热门搜索信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public Map<String,Object> list(@RequestParam(value = "page",required = false)Integer page,@RequestParam(value = "limit",required = false)Integer limit)throws Exception{
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<HotSearch> hotSearchList = hotSearchService.adminList(page, limit, Sort.Direction.ASC, "sort");
        Long count = hotSearchService.getAdminCount();
        resultMap.put("code",0);
        resultMap.put("count",count);
        resultMap.put("data",hotSearchList);
        return resultMap;
    }

    /**
     * 添加或者修改热门搜索
     * @param hotSearch
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public Map<String,Object> save(HotSearch hotSearch, HttpServletRequest request)throws Exception{
        hotSearchService.save(hotSearch);
        initSystem.loadData(request.getServletContext());
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 删除热门搜索
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String,Object> delete(Integer id,HttpServletRequest request)throws Exception{
        hotSearchService.delete(id);
        initSystem.loadData(request.getServletContext());
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 根据id获取实体信息
     * @param id
     * @return
     * @throws Exception
     */
     @RequestMapping("/findById")
     public Map<String,Object> findById(Integer id)throws Exception{
         HotSearch hotSearch = hotSearchService.get(id);
         Map<String,Object> resultMap=new HashMap<>();
         resultMap.put("hotSearch",hotSearch);
         resultMap.put("success",true);
         return resultMap;
     }
}
