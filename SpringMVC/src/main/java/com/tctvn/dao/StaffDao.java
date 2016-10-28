package com.tctvn.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tctvn.entity.Staff;

public class StaffDao extends BaseDao<Staff>{
    public Staff getStaff(Integer id) {
        Session session = super.getSessionFactory().getCurrentSession();
        Query query = session.createQuery("from Staff where id = :id");
        query.setParameter("id", (Integer) id);
        Staff staff = null;
        try {
            staff = (Staff) query.getSingleResult();
        } catch (NoResultException e) {
            
        }
        return staff;
    }
    
    public Staff findDuplicateStaff(String name, String katakana, Date birthday) {
        Session session = super.getSessionFactory().getCurrentSession();
        Query query = session.createQuery("from Staff where name = :name AND kananame = :katakana AND birthday = :birthday ");
        query.setParameter("name", name);
        query.setParameter("katakana", katakana);
        query.setParameter("birthday", birthday);
        Staff staff = null;
        try {
            staff = (Staff) query.getSingleResult();
        } catch (NoResultException e) {
            
        }
        return staff;
    }
    
    public List<Staff> listStaff() {
        Session session = super.getSessionFactory().getCurrentSession();
        List list = session.createQuery("from Staff ORDER BY id").list();
        return list;
    }
}
