package com.moking.workbench.service.impl;

import com.moking.settings.dao.UserDao;
import com.moking.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService{
    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> getUserList() {
        Map<String, Object> map=new HashMap<>();
        List list=userDao.getUserList();
        map.put("uList",list);
        return map;
    }
}
