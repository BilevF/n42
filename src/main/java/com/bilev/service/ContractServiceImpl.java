package com.bilev.service;

import com.bilev.dao.ContractDao;
import com.bilev.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService{

    @Autowired
    private ContractDao contractDao;

    @Override
    public List<Contract> getUserContracts(int userId) {
        return contractDao.getUserContracts(userId);
    }
}
