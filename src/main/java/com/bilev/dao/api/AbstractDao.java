package com.bilev.dao.api;

import com.bilev.exception.UnableToRemoveException;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;

import java.io.Serializable;

public interface AbstractDao<PK extends Serializable, T> {

    T getByKey(PK key) throws NotFoundException;

    void persist(T entity) throws UnableToSaveException;

    void save(T entity) throws UnableToSaveException;

    void saveOrUpdate(T entity) throws UnableToSaveException;

    void update(T entity) throws UnableToUpdateException;

    void delete(T entity) throws UnableToRemoveException;
}
