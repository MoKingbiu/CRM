package com.moking.settings.web.controller;

import com.moking.settings.domain.User;
import com.moking.settings.service.UserService;
import com.moking.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class UserController {
    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map login(User u, HttpSession session, HttpServletRequest request){
        String ip=request.getRemoteAddr();
        String loginPwd= MD5Util.getMD5(u.getLoginPwd());
        User user= null;
        Map map=new HashMap();

        try {
            user = userServiceImpl.login(u.getLoginAct(),loginPwd,ip);
            session.setAttribute("user",user);
            map.put("success",true);
            map.put("msg","");
        } catch (com.moking.exception.exception e) {
            map.put("success",false);
            map.put("msg",e.getMessage());
        }finally {
            return map;
        }
    }
    
}
