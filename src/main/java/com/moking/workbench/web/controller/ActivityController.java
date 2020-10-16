package com.moking.workbench.web.controller;

import com.moking.settings.domain.User;
import com.moking.settings.service.UserService;
import com.moking.vo.pageListVo;
import com.moking.workbench.domain.Activity;
import com.moking.workbench.service.ActivityService;
import com.moking.workbench.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    UserService userServiceImpl;
    @Autowired
    ActivityService activityServiceImpl;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> list=null;
        list=userServiceImpl.getUserList();
        return list;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String, Boolean> save(Activity activity, HttpSession session){
        boolean flag=activityServiceImpl.save(activity,session);
        Map<String,Boolean> map=new HashMap();
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public pageListVo<Activity> pageList(Activity activity ,HttpServletRequest request){
        String pageNostr=request.getParameter("pageNo");
        String pageSizestr=request.getParameter("pageSize");
        int pageNo=Integer.parseInt(pageNostr);
        int pageSize=Integer.parseInt(pageSizestr);
        int skipSize=(pageNo-1)*pageSize;

        Map map=new HashMap();
        map.put("activity",activity);
        map.put("skipSize",skipSize);
        map.put("pageSize",pageSize);
        pageListVo vo=activityServiceImpl.pageList(map);
        return vo;
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String,Boolean> delete(HttpServletRequest request){
        Map map=new HashMap();
        String[] ids=request.getParameterValues("id");
        Boolean flag= null;
        try {
            flag = activityServiceImpl.delete(ids);
            map.put("success",flag);
        } catch (Exception e) {
            map.put("success",false);
        } finally{
            return map;
        }
    }

}
