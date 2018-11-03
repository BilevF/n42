package com.bilev.service.impl;

import com.bilev.dao.api.*;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.model.*;
import com.bilev.service.api.ContractService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Contract> getUserContracts(int userId) {
        return contractDao.getUserContracts(userId);
    }

    // Get

    @Override
    @Transactional(readOnly = true)
    public ContractDto getContract(int contractId) {
        Contract contract = contractDao.findById(contractId);

        return modelMapper.map(contract, ContractDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getAvailableOptionsForContract(int contractId) {

        Contract contract = contractDao.findById(contractId);
        Set<Option> contractOptions = new HashSet<>(contract.getOptions());
        Set<Option> availableOptions = new HashSet<>(contract.getTariff().getOptions());

        availableOptions.removeAll(contractOptions);

        for (Option contractOption : contractOptions) {
            availableOptions.removeAll(contractOption.getIncompatibleOptions());
        }

        for (Iterator<Option> it = availableOptions.iterator(); it.hasNext();) {
            Option option = it.next();
            if (!contractOptions.containsAll(option.getRequiredOptions())) {
                it.remove();
            }
        }

        return modelMapper.map(availableOptions, new TypeToken<Set<BasicOptionDto>>() {}.getType());
    }

    // EDIT

    @Override
    @Transactional
    public void changeContractTariff(int contractId, int replacementTariffId) {
        Tariff tariff = tariffDao.findById(replacementTariffId);
        Contract contract = contractDao.findById(contractId);
        contract.setTariff(tariff);
        contractDao.update(contract);
    }

    @Override
    @Transactional
    public void addOption(int contractId, int optionId) {
        Option option = optionDao.findById(optionId);
        Contract contract = contractDao.findById(contractId);

        if (option.getConnectionPrice() > contract.getBalance()) {
            // Error
            return;
        }

        if (contract.getOptions().contains(option)) {
            // Error
            return;
        }
        contract.getOptions().add(option);
        contractDao.update(contract);
    }

    @Override
    public void removeOption(int contractId, int optionId) {
        Option option = optionDao.findById(optionId);
        Contract contract = contractDao.findById(contractId);

        if (contract.getOptions().containsAll(option.getRequiredOptionsOf())) {
            // Error
            return;
        }

        contract.getOptions().remove(option);
        contractDao.update(contract);
    }

    @Override
    public void changeBlockStatus(int requestedUserId, int contractId) {
        User user = userDao.findById(requestedUserId);
        Contract contract = contractDao.findById(contractId);

        switch (user.getRole().getRoleName()) {
            case ROLE_ADMIN:
                switch (contract.getBlock().getBlockType()) {
                    case ADMIN_BLOCK:
                    case CLIENT_BLOCK:
                        contract.setBlock(blockDao.getBlockByType(Block.BlockType.NON));
                        break;
                    case NON:
                        contract.setBlock(blockDao.getBlockByType(Block.BlockType.ADMIN_BLOCK));
                        break;
                }
                break;
            case ROLE_CLIENT:
                switch (contract.getBlock().getBlockType()) {
                    case ADMIN_BLOCK:
                        // Error
                        break;
                    case CLIENT_BLOCK:
                        contract.setBlock(blockDao.getBlockByType(Block.BlockType.NON));
                        break;
                    case NON:
                        contract.setBlock(blockDao.getBlockByType(Block.BlockType.CLIENT_BLOCK));
                        break;
                }
                break;
        }
        contractDao.update(contract);
    }

    @Override
    public void addMoney(int contractId, double amount) {
        Contract contract = contractDao.findById(contractId);

        if (amount <= 0) {
            // ERROR
            return;
        }

        contract.setBalance(contract.getBalance() + amount);
        contractDao.update(contract);
    }
}
