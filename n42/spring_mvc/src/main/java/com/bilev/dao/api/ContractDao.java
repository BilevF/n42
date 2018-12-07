package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.model.Contract;

import java.util.Collection;
import java.util.List;

public interface ContractDao extends AbstractDao<Integer, Contract> {
    List<Contract> getUserContracts(int userId) throws UnableToFindException;

    Contract getContractByPhone(String phone) throws UnableToFindException;

    void updateAll(Collection<Contract> contracts) throws UnableToUpdateException;

    List<Contract> getAllContractsWithOption(int optionId) throws UnableToFindException;
}
