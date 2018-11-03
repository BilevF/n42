package com.bilev.dao.api;

import com.bilev.model.Contract;

import java.util.List;

public interface ContractDao {
    List<Contract> getUserContracts(int userId);

    Contract findById(int id);

    void saveOrUpdate(Contract contract);

    void update(Contract contract);

    void updateAll(List<Contract> contracts);


}
