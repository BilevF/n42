package com.bilev.service;

import com.bilev.model.Contract;

import java.util.List;

public interface ContractService {
    List<Contract> getUserContracts(int userId);
}
