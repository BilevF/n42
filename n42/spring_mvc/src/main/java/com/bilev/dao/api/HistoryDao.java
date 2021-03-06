package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.History;

import java.util.List;

public interface HistoryDao extends AbstractDao<Integer, History> {

    List<History> getContractHistory(int contractId) throws UnableToFindException;

}
