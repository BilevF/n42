package com.bilev.dao;

import com.bilev.model.Contract;

import java.util.List;

public interface ContractDao {
    List<Contract> getUserContracts(int userId);
}
