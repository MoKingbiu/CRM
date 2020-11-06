package com.moking.workbench.service.impl;

import com.moking.workbench.dao.CustomerDao;
import com.moking.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {
        List<String> list=customerDao.getCustomerName(name);
        return list;
    }
}
