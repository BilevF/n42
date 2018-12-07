package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToRemoveException;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;

import java.io.Serializable;

public interface AbstractDao<PK extends Serializable, T> {

    T getByKey(PK key) throws UnableToFindException;

    void persist(T entity) throws UnableToSaveException;

    void saveOrUpdate(T entity) throws UnableToSaveException;

    void update(T entity) throws UnableToUpdateException;

    void delete(T entity) throws UnableToRemoveException;
}
