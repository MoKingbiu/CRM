package com.moking.workbench.dao;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);
    int delete(String[] ids);
}
