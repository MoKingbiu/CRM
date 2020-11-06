package com.moking.workbench.service.impl;

import com.moking.exception.clueTranException;
import com.moking.exception.exception;
import com.moking.settings.dao.UserDao;
import com.moking.settings.domain.User;
import com.moking.utils.DateTimeUtil;
import com.moking.utils.UUIDUtil;
import com.moking.workbench.dao.*;
import com.moking.workbench.domain.*;
import com.moking.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Override
    public Map<String, Object> getUserList() {
        Map<String, Object> map=new HashMap<>();
        List list=userDao.getUserList();
        map.put("uList",list);
        return map;
    }

    @Override
    @Transactional(
            rollbackFor = exception.class
    )
    public Map<String, Object> saveClue(Clue clue, HttpSession session) {
        Map<String, Object> map=new HashMap<>();
        boolean flag=true;

        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        User user= (User) session.getAttribute("user");
        clue.setCreateBy(user.getName());
        int count=clueDao.saveClue(clue);
        if(count!=1){
            flag=false;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public Clue getClue(String id) {
        Clue clue=clueDao.getClue(id);
        return clue;
    }

    @Override
    public int unbund(String id) {
        int count=clueDao.unbund(id);
        return count;
    }

    @Override
    public boolean bund(String cid,String[] aids) {
        boolean flag=true;
        ClueActivityRelation car=new ClueActivityRelation();
        car.setClueId(cid);
        for(int i=0;i<aids.length;i++){
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aids[i]);
            int count=clueDao.bund(car);
            if(count!=1){
                flag=false;
            }
        }
        return flag;
    }

    @Override
    @Transactional(
            rollbackFor = exception.class
    )
    public void tran(String cid, Tran t, String name) throws exception {
        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueDao.getClueById(cid);

        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        String company=clue.getCompany();
        Customer customer=customerDao.getByName(company);
        if(customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(company);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(name);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());

            int count1=customerDao.save(customer);
            if(count1!=1){
                throw new clueTranException("客户创建失败");
            }
        }

        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts con=new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setOwner(clue.getOwner());
        con.setSource(clue.getSource());
        con.setCustomerId(customer.getId());
        con.setFullname(clue.getFullname());
        con.setAppellation(clue.getAppellation());
        con.setEmail(clue.getEmail());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setCreateBy(name);
        con.setCreateTime(DateTimeUtil.getSysTime());
        con.setDescription(clue.getDescription());
        con.setContactSummary(clue.getContactSummary());
        con.setNextContactTime(clue.getNextContactTime());
        con.setAddress(clue.getAddress());

        int count2=contactsDao.save(con);
        if(count2!=1){
            throw new clueTranException("联系人创建失败");
        }

        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList=clueRemarkDao.getList(cid);
        for(ClueRemark clueRemark : clueRemarkList){
            String noteContent=clueRemark.getNoteContent();

            //客户
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(name);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            int count3=customerRemarkDao.save(customerRemark);
            if(count3!=1){
                throw new clueTranException("创建客户备注失败");
            }

            //联系人
            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(name);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(con.getId());
            int count4=contactsRemarkDao.save(contactsRemark);
            if(count4!=1){
                throw new clueTranException("创建联系人备注失败");
            }

        }

        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> list2=clueActivityRelationDao.getListById(cid);
        int num=0;
        for(ClueActivityRelation c : list2){
            ContactsActivityRelation cc=new ContactsActivityRelation();
            cc.setId(UUIDUtil.getUUID());
            cc.setContactsId(con.getId());
            cc.setActivityId(c.getActivityId());
            int count5=contactsActivityRelationDao.save(cc);
            if(count5!=1){
                throw new clueTranException("关系转换失败");
            }
            num++;
        }

        //(6) 如果有创建交易需求，创建一条交易
        if(t!=null){
            t.setId(UUIDUtil.getUUID());
            t.setOwner(clue.getOwner());
            t.setCustomerId(customer.getId());
            t.setContactsId(con.getId());
            t.setCreateBy(name);
            t.setCreateTime(DateTimeUtil.getSysTime());
            t.setDescription(clue.getDescription());
            t.setContactSummary(clue.getContactSummary());
            t.setNextContactTime(clue.getNextContactTime());
            int count6=tranDao.save(t);
            if(count6!=1){
                throw new clueTranException("创建交易失败");
            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory th=new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setStage(t.getStage());
            th.setMoney(t.getMoney());
            th.setExpectedDate(t.getExpectedDate());
            th.setCreateBy(t.getCreateBy());
            th.setCreateTime(t.getCreateTime());
            th.setTranId(t.getId());
            int count7=tranHistoryDao.save(th);
            if(count7!=1){
                throw new clueTranException("创建交易历史失败");
            }
        }

        //(8) 删除线索备注
        int count8=clueRemarkDao.delete(cid);
        if(count8!=clueRemarkList.size()){
            throw new clueTranException("线索备注删除失败");
        }

        //(9) 删除线索和市场活动的关系
        int count9=clueActivityRelationDao.delete(cid);
        if(count9!=num){
            throw new clueTranException("线索关系删除失败");
        }

        //(10) 删除线索
        int count10=clueDao.delete(cid);
        if(count10!=1){
            throw new clueTranException("线索删除失败");
        }
    }
}
