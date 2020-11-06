package com.moking.workbench.web.controller;

import com.moking.settings.domain.User;
import com.moking.utils.DateTimeUtil;
import com.moking.utils.UUIDUtil;
import com.moking.workbench.domain.Tran;
import com.moking.workbench.service.CustomerService;
import com.moking.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
    @Autowired
    private TranService tranServiceImpl;
    @Autowired
    private CustomerService customerServiceImpl;


    @RequestMapping("/add.do")
    public String add(HttpSession session){
        List<User> uList=tranServiceImpl.add();
        session.setAttribute("uList",uList);
        return "/workbench/transaction/save.jsp";
    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(String name){
        List<String> list=customerServiceImpl.getCustomerName(name);
        return list;
    }

    @RequestMapping("/save.do")
    public ModelAndView save(Tran t, String customerName,HttpSession session){
        User user= (User) session.getAttribute("user");
        t.setCreateTime(DateTimeUtil.getSysTime());
        t.setCreateBy(user.getName());
        ModelAndView mv=new ModelAndView();
        try {
            tranServiceImpl.save(t,customerName);
            mv.setViewName("redirect:"+"/workbench/transaction/index.jsp");
        } catch (com.moking.exception.exception exception) {
            mv.addObject("info","交易创建失败，原因："+exception.getMessage());
            mv.setViewName("/workbench/info/info.jsp");
        } finally{
            return mv;
        }
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Tran t=tranServiceImpl.detail(id);
        mv.addObject("t",t);
        mv.setViewName("/workbench/transaction/detail.jsp");
        return mv;
    }
}
