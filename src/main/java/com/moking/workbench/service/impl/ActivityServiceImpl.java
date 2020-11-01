package com.moking.workbench.service.impl;

import com.moking.exception.activityDeleteException;
import com.moking.settings.domain.User;
import com.moking.utils.DateTimeUtil;
import com.moking.utils.UUIDUtil;
import com.moking.vo.pageListVo;
import com.moking.workbench.dao.ActivityDao;
import com.moking.workbench.dao.ActivityRemarkDao;
import com.moking.workbench.domain.Activity;
import com.moking.workbench.domain.ActivityRemark;
import com.moking.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Override
    @Transactional
    public boolean save(Activity activity, HttpSession session) {
        boolean flag=false;
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        User user= (User) session.getAttribute("user");
        activity.setCreateBy(user.getName());

        if(
            "".equals(activity.getName()) ||
            "".equals(activity.getCost()) ||
            "".equals(activity.getDescription()) ||
            "点击选择时间".equals(activity.getStartDate()) ||
            "点击选择时间".equals(activity.getEndDate()) ||
            activity.getStartDate().compareTo(activity.getEndDate())>0
        ){
            return flag;
        }

        int count=activityDao.save(activity);
        if(count==1){
            flag=true;
        }
        return flag;
    }

    @Override
    public pageListVo<Activity> pageList(Map map) {
        pageListVo<Activity> vo=new pageListVo<>();
        List<Activity> list=activityDao.pageList(map);
        int total=activityDao.getPageTotal(map);
        vo.setList(list);
        vo.setTotal(total);
        return vo;
    }

    @Override
    @Transactional
    public boolean delete(String[] ids) throws Exception {
        boolean flag=true;
        //确定备注表数目
        int count1=activityRemarkDao.getCountByAids(ids);
        //删除备注表
        if(count1>0){
            int count2=activityRemarkDao.delete(ids);
            if(count1!=count2){
                throw new activityDeleteException("活动删除失败");
            }
        }
        //删除活动表
        int count3=activityDao.delete(ids);
        if(count3!=ids.length){
            throw new activityDeleteException("活动删除失败");
        }
        return flag;
    }

    @Override
    public Activity getActivityById(String id) {
        Activity activity=activityDao.getActivityById(id);
        return activity;
    }

    @Override
    @Transactional
    public boolean update(Activity activity, HttpSession session) {
        boolean flag=false;
        activity.setId(activity.getId());
        activity.setEditTime(DateTimeUtil.getSysTime());
        User user= (User) session.getAttribute("user");
        activity.setEditBy(user.getName());

        if(
                "".equals(activity.getName()) ||
                "".equals(activity.getOwner()) ||
                activity.getOwner()==null ||
                activity.getStartDate().compareTo(activity.getEndDate())>0
        ){
            return flag;
        }

        int count=activityDao.update(activity);
        if(count==1){
            flag=true;
        }
        return flag;
    }

    @Override
    public Activity getActivityById2(String id) {
        Activity activity=activityDao.getActivityById2(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkList(String id) {
        List<ActivityRemark> list=activityRemarkDao.getRemarkList(id);
        return list;
    }

    @Override
    @Transactional
    public boolean deleteRemark(String id) {
        boolean flag=true;
        int count=activityRemarkDao.deleteRemark(id);
        if(count!=1){
            flag=false;
        }
        return flag;
    }


    @Override
    @Transactional
    public Map<String, Object> saveRemark(ActivityRemark activityRemark,HttpSession session) {
        Map<String,Object> map=new HashMap<>();
        boolean flag=true;
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        User user= (User) session.getAttribute("user");
        activityRemark.setCreateBy(user.getName());
        activityRemark.setEditFlag("0");
        int count=activityRemarkDao.saveRemark(activityRemark);
        if(count!=1){
            flag=false;
        }
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updateRemark(ActivityRemark activityRemark,HttpSession session) {
        Map<String,Object> map=new HashMap<>();
        boolean flag=true;

        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        User user= (User) session.getAttribute("user");
        activityRemark.setEditBy(user.getName());
        activityRemark.setEditFlag("1");

        int count=activityRemarkDao.updateRemark(activityRemark);
        if(count!=1){
            flag=false;
        }
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }

    @Override
    public List getAcByClueId(String id) {
        List list=activityDao.getAcByClueId(id);
        return list;
    }

    @Override
    public List getAcListNotInClue(Map<String, String> map) {
        List list=activityDao.getAcListNotInClue(map);
        return list;
    }

    @Override
    public List<Activity> getAcList(String aname) {
        List<Activity> list=activityDao.getAcList(aname);
        return list;
    }
}
