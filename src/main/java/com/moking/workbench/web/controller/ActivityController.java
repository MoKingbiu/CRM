package com.moking.workbench.web.controller;

import com.moking.settings.domain.User;
import com.moking.settings.service.UserService;
import com.moking.vo.pageListVo;
import com.moking.workbench.domain.Activity;
import com.moking.workbench.domain.ActivityRemark;
import com.moking.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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

    @RequestMapping("/edit.do")
    @ResponseBody
    public Map<String,Object> edit(String id){
        Map<String,Object> map=new HashMap<>();
        List ulist=userServiceImpl.getUserList();
        Activity activity=activityServiceImpl.getActivityById(id);
        map.put("uList",ulist);
        map.put("ac",activity);
        return map;
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String, Boolean> update(Activity activity, HttpSession session){
        boolean flag=activityServiceImpl.update(activity,session);
        Map<String,Boolean> map=new HashMap();
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Activity activity=activityServiceImpl.getActivityById2(id);
        mv.addObject("ac",activity);
        mv.setViewName("/workbench/activity/detail.jsp");
        return mv;
    }

    @RequestMapping("/getRemarkList.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkList(String id){
        List<ActivityRemark> list=activityServiceImpl.getRemarkList(id);
        return list;
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    public Map<String,Boolean> deleteRemark(String id){
        boolean flag=activityServiceImpl.deleteRemark(id);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Map<String,Object> saveRemark(ActivityRemark activityRemark,HttpSession session){
        Map<String,Object> map=activityServiceImpl.saveRemark(activityRemark,session);
        return map;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark activityRemark,HttpSession session){
        Map<String,Object> map=activityServiceImpl.updateRemark(activityRemark,session);
        return map;
    }
}
