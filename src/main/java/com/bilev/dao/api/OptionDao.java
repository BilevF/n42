package com.bilev.dao.api;

import com.bilev.model.Option;

import java.util.Collection;
import java.util.List;

public interface OptionDao extends AbstractDao<Integer, Option> {

    void removeAll(Collection<Option> options);

}
