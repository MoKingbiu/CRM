package com.moking.workbench.dao;

import com.moking.workbench.domain.Activity;
import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);
    List<Activity> pageList(Map map);
    int getPageTotal(Map map);
    int delete(String[] ids);
    Activity getActivityById(String id);
    int update(Activity activity);
    Activity getActivityById2(String id);
    List getAcByClueId(String id);
    List getAcListNotInClue(Map<String, String> map);
    List<Activity> getAcList(String aname);
}
