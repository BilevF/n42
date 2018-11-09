package com.bilev.dao.api;

import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToUpdateException;
import com.bilev.model.Contract;

import java.util.Collection;
import java.util.List;

public interface ContractDao extends AbstractDao<Integer, Contract> {
    List<Contract> getUserContracts(int userId);

    Contract getContractByPhone(String phone) throws NotFoundException;

    void updateAll(Collection<Contract> contracts) throws UnableToUpdateException;

    List<Contract> getAllContractsWithOption(int optionId);
}
