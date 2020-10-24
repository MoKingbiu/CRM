package com.moking.workbench.service.impl;

import com.moking.settings.dao.UserDao;
import com.moking.settings.domain.User;
import com.moking.utils.DateTimeUtil;
import com.moking.utils.UUIDUtil;
import com.moking.workbench.dao.ClueDao;
import com.moking.workbench.domain.Clue;
import com.moking.workbench.domain.ClueActivityRelation;
import com.moking.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private ClueDao clueDao;

    @Override
    public Map<String, Object> getUserList() {
        Map<String, Object> map=new HashMap<>();
        List list=userDao.getUserList();
        map.put("uList",list);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> saveClue(Clue clue, HttpSession session) {
        Map<String, Object> map=new HashMap<>();
        boolean flag=true;

        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        User user= (User) session.getAttribute("user");
        clue.setCreateBy(user.getName());
        int count=clueDao.saveClue(clue);
        if(count!=1){
            flag=false;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public Clue getClue(String id) {
        Clue clue=clueDao.getClue(id);
        return clue;
    }

    @Override
    public int unbund(String id) {
        int count=clueDao.unbund(id);
        return count;
    }

    @Override
    public boolean bund(String cid,String[] aids) {
        boolean flag=true;
        ClueActivityRelation car=new ClueActivityRelation();
        car.setClueId(cid);
        for(int i=0;i<aids.length;i++){
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aids[i]);
            int count=clueDao.bund(car);
            if(count!=1){
                flag=false;
            }
        }
        return flag;
    }
}
