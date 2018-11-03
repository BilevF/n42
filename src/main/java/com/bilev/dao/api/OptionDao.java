package com.bilev.dao.api;

import com.bilev.model.Option;

import java.util.List;

public interface OptionDao {
    void saveOrUpdate(Option option);

    void remove(Option option);

    void removeAll(List<Option> options);

    Option findById(int id);

}
