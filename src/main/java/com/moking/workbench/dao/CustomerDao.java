package com.moking.workbench.dao;

import com.moking.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getByName(String company);

    int save(Customer customer);

    List<String> getCustomerName(String name);
}
