package com.moking.workbench.service;

import com.moking.vo.pageListVo;
import com.moking.workbench.domain.Activity;
import com.moking.workbench.domain.ActivityRemark;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity, HttpSession session);
    pageListVo<Activity> pageList(Map map);
    boolean delete(String[] ids) throws Exception;
    Activity getActivityById(String id);
    boolean update(Activity activity, HttpSession session);
    Activity getActivityById2(String id);
    List<ActivityRemark> getRemarkList(String id);
    boolean deleteRemark(String id);
    Map<String, Object> saveRemark(ActivityRemark activityRemark,HttpSession session);
    Map<String, Object> updateRemark(ActivityRemark activityRemark,HttpSession session);
    List getAcByClueId(String id);
    List getAcListNotInClue(Map<String, String> map);
}
