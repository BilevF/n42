package com.bilev.dao;

import com.bilev.model.Option;
import org.springframework.stereotype.Repository;

@Repository("optionDao")
public class OptionDaoImpl extends AbstractDao<Integer, Option> implements OptionDao {
    @Override
    public void save(Option option) {
        persist(option);
    }

    @Override
    public Option findById(int id) {
        return getByKey(id);
    }


}
