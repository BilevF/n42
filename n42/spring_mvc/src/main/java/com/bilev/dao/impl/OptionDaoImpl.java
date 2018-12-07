package com.bilev.dao.impl;

import com.bilev.dao.api.OptionDao;
import com.bilev.exception.dao.UnableToRemoveException;
import com.bilev.model.Option;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("optionDao")
public class OptionDaoImpl extends AbstractDaoImpl<Integer, Option> implements OptionDao {

    @Override
    public void removeAll(Collection<Option> options) throws UnableToRemoveException {
        for (Option option : options)
            delete(option);
    }

}
