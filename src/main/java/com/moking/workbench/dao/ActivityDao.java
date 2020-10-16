package com.moking.workbench.dao;

import com.moking.workbench.domain.Activity;
import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);
    List<Activity> pageList(Map map);
    int getPageTotal(Map map);
    int delete(String[] ids);
}
