package com.moking.workbench.service.impl;

import com.moking.exception.exception;
import com.moking.exception.tranSaveException;
import com.moking.settings.dao.UserDao;
import com.moking.settings.domain.User;
import com.moking.utils.DateTimeUtil;
import com.moking.utils.UUIDUtil;
import com.moking.workbench.dao.CustomerDao;
import com.moking.workbench.dao.TranDao;
import com.moking.workbench.dao.TranHistoryDao;
import com.moking.workbench.domain.Customer;
import com.moking.workbench.domain.Tran;
import com.moking.workbench.domain.TranHistory;
import com.moking.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;


    @Override
    public List<User> add() {
        List<User> list=userDao.getUserList();
        return list;
    }

    @Override
    @Transactional(
            rollbackFor = exception.class
    )
    public void save(Tran t, String customerName) throws exception {
        Customer customer=customerDao.getByName(customerName);
        if(customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateBy(t.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setOwner(t.getOwner());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setContactSummary(t.getContactSummary());
            customer.setDescription(t.getDescription());

            int count1=customerDao.save(customer);
            if(count1!=1){
                throw new tranSaveException("客户创建失败");
            }
        }

        t.setId(UUIDUtil.getUUID());
        t.setCustomerId(customer.getId());
        int count2=tranDao.save(t);
        if(count2!=1){
            throw new tranSaveException("交易创建失败");
        }

        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateBy(t.getCreateBy());
        th.setCreateTime(DateTimeUtil.getSysTime());

        int count3=tranHistoryDao.save(th);
        if(count3!=1){
            throw new tranSaveException("交易历史创建失败");
        }
    }

    @Override
    public Tran detail(String id) {
        Tran t=tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> showHistoryList(String tranId) {
        List<TranHistory> list=tranHistoryDao.showHistoryList(tranId);
        return list;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag=true;

        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setTranId(t.getId());
        int count=tranHistoryDao.save(th);
        if(count!=1){
            flag=false;
        }

        int count2=tranDao.changeStage(t);
        if(count2!=1){
            flag=false;
        }
        return flag;
    }
}
