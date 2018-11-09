package com.bilev.dao.impl;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;

import com.bilev.exception.UnableToRemoveException;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;
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
    public T getByKey(PK key) throws NotFoundException {
        try {
            T t = (T) getSession().get(persistentClass, key);
            if (t == null) throw new NotFoundException();
            return t;
        } catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }

    public void persist(T entity) throws UnableToSaveException {
        try {
            getSession().persist(entity);
        } catch (Exception ex) {
            throw new UnableToSaveException(ex);
        }
    }

    public void save(T entity) throws UnableToSaveException {
        try {
            getSession().save(entity);
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
