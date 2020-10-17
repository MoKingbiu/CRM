package com.moking.workbench.service;

import com.moking.vo.pageListVo;
import com.moking.workbench.domain.Activity;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity, HttpSession session);
    pageListVo<Activity> pageList(Map map);
    boolean delete(String[] ids) throws Exception;
    Activity getActivityById(String id);
    boolean update(Activity activity, HttpSession session);
}
