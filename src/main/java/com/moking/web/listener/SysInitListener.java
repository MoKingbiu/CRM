package com.moking.web.listener;

import com.moking.settings.domain.DicValue;
import com.moking.settings.service.impl.DicServiceImpl;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {
    private DicServiceImpl dicService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //获取业务类
        ServletContext application=event.getServletContext();
        WebApplicationContext ac=WebApplicationContextUtils.getRequiredWebApplicationContext(application);
        dicService=ac.getBean(DicServiceImpl.class);

        //加载数据字典
        Map<String, List<DicValue>> map= dicService.getAll();
        Set<String> set=map.keySet();
        for(String code: set){
            application.setAttribute(code,map.get(code));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
