package com.bilev.dao.impl;

import com.bilev.dao.api.OptionDao;
import com.bilev.model.Option;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("optionDao")
public class OptionDaoImpl extends AbstractDao<Integer, Option> implements OptionDao {
    @Override
    public void saveOrUpdate(Option option) {
        super.saveOrUpdate(option);
    }

    @Override
    public void remove(Option option) {
        delete(option);
    }

    @Override
    public void removeAll(List<Option> options) {
        for (Option option : options)
            remove(option);
    }

    @Override
    public Option findById(int id) {
        return getByKey(id);
    }

}
