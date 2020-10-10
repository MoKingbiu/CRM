package com.moking.workbench.web.controller;

import com.moking.settings.domain.User;
import com.moking.settings.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> list=null;
        list=userServiceImpl.getUserList();
        return list;
    }

}
