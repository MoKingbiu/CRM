package com.moking.workbench.web.controller;

import com.moking.exception.exception;
import com.moking.settings.domain.User;
import com.moking.workbench.domain.Activity;
import com.moking.workbench.domain.Clue;
import com.moking.workbench.domain.Tran;
import com.moking.workbench.service.ActivityService;
import com.moking.workbench.service.ClueService;
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
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueServiceImpl;
    @Autowired
    private ActivityService activityServiceImpl;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public Map<String,Object> getUserList(){
        Map<String,Object> map=clueServiceImpl.getUserList();
        return map;
    }

    @RequestMapping("/saveClue.do")
    @ResponseBody
    public Map<String,Object> saveClue(Clue clue, HttpSession session){
        Map<String,Object> map=clueServiceImpl.saveClue(clue,session);
        return map;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Clue clue=clueServiceImpl.getClue(id);
        mv.addObject("c",clue);
        mv.setViewName("/workbench/clue/detail.jsp");
        return mv;
    }

    @RequestMapping("/getAcByClueId.do")
    @ResponseBody
    public List getAcByClueId(String id){
        List list=activityServiceImpl.getAcByClueId(id);
        return list;
    }

    @RequestMapping("/unbund.do")
    @ResponseBody
    public Map<String,Object> unbund(String id){
        Map<String,Object> map=new HashMap<>();
        boolean flag=true;

        int count=clueServiceImpl.unbund(id);
        if(count!=1){
            flag=false;
        }
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/getAcListNotInClue.do")
    @ResponseBody
    public List getAcListNotInClue(String aname,String clueId){
        Map<String,String> map=new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);
        List list=activityServiceImpl.getAcListNotInClue(map);
        return list;
    }

    @RequestMapping("/bund.do")
    @ResponseBody
    public Map<String,Object> bund(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        boolean flag=true;
        String cid=request.getParameter("cid");
        String[] aids=request.getParameterValues("aid");

        flag=clueServiceImpl.bund(cid,aids);
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/getAcList.do")
    @ResponseBody
    public List<Activity> getAcList(String aname){
        List<Activity> list=activityServiceImpl.getAcList(aname);
        return list;
    }

    @RequestMapping("/tran.do")
    public ModelAndView tran(String cid, boolean flag, Tran tran,HttpSession session){
        ModelAndView mv=new ModelAndView();
        User user= (User) session.getAttribute("user");
        Tran t=null;
        if(flag){
            t=tran;
        }

        try {
            clueServiceImpl.tran(cid,t,user.getName());
            mv.setViewName("redirect:"+"/workbench/clue/index.jsp");
        } catch (exception e) {
            String message=e.getMessage();
            mv.addObject("info","线索转换失败,原因:"+message);
            mv.setViewName("/workbench/info/info.jsp");
        } finally {
            return mv;
        }
    }
}
