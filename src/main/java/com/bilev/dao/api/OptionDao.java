package com.bilev.dao.api;

import com.bilev.exception.UnableToRemoveException;
import com.bilev.model.Option;

import java.util.Collection;

public interface OptionDao extends AbstractDao<Integer, Option> {

    void removeAll(Collection<Option> options) throws UnableToRemoveException;

}
