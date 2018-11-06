package com.bilev.dao.api;

import java.io.Serializable;

public interface AbstractDao<PK extends Serializable, T> {

    T getByKey(PK key);

    void persist(T entity);

    void save(T entity);

    void saveOrUpdate(T entity);

    void update(T entity);

    void delete(T entity);
}
