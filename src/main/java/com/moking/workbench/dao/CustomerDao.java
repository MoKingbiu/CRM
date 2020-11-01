package com.moking.workbench.dao;

import com.moking.workbench.domain.Customer;

public interface CustomerDao {

    Customer getByName(String company);

    int save(Customer customer);
}
