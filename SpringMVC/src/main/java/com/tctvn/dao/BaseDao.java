package com.tctvn.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseDao<T> {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void insert(T t) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(t);
    }

    public void update(T t) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(t);
    }

    public void delete(T t) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(t);
    }
}