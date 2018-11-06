package com.bilev.dao.api;

import com.bilev.model.Contract;

import java.util.Collection;
import java.util.List;

public interface ContractDao extends AbstractDao<Integer, Contract> {
    List<Contract> getUserContracts(int userId);

    void updateAll(Collection<Contract> contracts);
}
