package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToRemoveException;
import com.bilev.model.Option;

import java.util.Collection;

public interface OptionDao extends AbstractDao<Integer, Option> {

    void removeAll(Collection<Option> options) throws UnableToRemoveException;

}
