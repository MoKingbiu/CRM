package com.moking.workbench.dao;

import com.moking.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);
    int delete(String[] ids);
    List<ActivityRemark> getRemarkList(String id);
    int deleteRemark(String id);
    int saveRemark(ActivityRemark activityRemark);
    int updateRemark(ActivityRemark activityRemark);
}
