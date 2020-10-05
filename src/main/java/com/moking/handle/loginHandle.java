package com.moking.handle;

import com.moking.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class loginHandle implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean pan=false;
        User user= (User) request.getSession().getAttribute("user");
        if(user!=null){
            pan=true;
        }else{
            //重定向
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
        return pan;
    }
}
