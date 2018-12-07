package com.bilev.dao.impl;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;

import com.bilev.exception.dao.UnableToRemoveException;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoImpl<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDaoImpl(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) throws UnableToFindException {
        try {
            return (T) getSession().get(persistentClass, key);

        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    public void persist(T entity) throws UnableToSaveException {
        try {
            getSession().persist(entity);
        } catch (Exception ex) {
            throw new UnableToSaveException(ex);
        }
    }

    public void saveOrUpdate(T entity) throws UnableToSaveException {
        try {
            getSession().saveOrUpdate(entity);
        } catch (Exception ex) {
            throw new UnableToSaveException(ex);
        }
    }

    public void update(T entity) throws UnableToUpdateException {
        try {
            getSession().update(entity);
        } catch (Exception ex) {
            throw new UnableToUpdateException(ex);
        }
    }

    public void delete(T entity) throws UnableToRemoveException {
        try {
            getSession().delete(entity);
        } catch (Exception ex) {
            throw new UnableToRemoveException(ex);
        }
    }

    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }

}
