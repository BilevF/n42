package com.bilev.dao;

import com.bilev.model.Option;

public interface OptionDao {
    void save(Option option);

    Option findById(int id);
}
