package com.moking.settings.service.impl;

import com.moking.settings.dao.DicTypeDao;
import com.moking.settings.dao.DicValueDao;
import com.moking.settings.domain.DicType;
import com.moking.settings.domain.DicValue;
import com.moking.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map=new HashMap<>();
        List<DicType> dtList=dicTypeDao.getDicTypeList();
        List<DicValue> dvList=null;
        for(DicType dicType: dtList){
            String code=dicType.getCode();
            dvList=dicValueDao.getDicValueList(code);
            map.put(code,dvList);
        }
        return map;
    }
}
