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
    private HistoryDao historyDao;

    @Autowired
    private ModelMapper modelMapper;

    // Get

    @Override
    @Transactional(readOnly = true)
    public ContractDto getContract(int contractId) {
        Contract contract = contractDao.getByKey(contractId);

        return modelMapper.map(contract, ContractDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getAvailableOptionsForContract(int contractId) {

        Contract contract = contractDao.getByKey(contractId);
        Set<Option> availableOptions = contract.getTariff().getOptions();

        Set<Option> selectedOptions = new HashSet<>();
        selectedOptions.addAll(contract.getOptions());
        selectedOptions.addAll(contract.getBasket());

        availableOptions.removeAll(selectedOptions);

        for (Option contractOption : selectedOptions) {
            availableOptions.removeAll(contractOption.getIncompatibleOptions());
        }

        for (Iterator<Option> it = availableOptions.iterator(); it.hasNext();) {
            Option option = it.next();
            if (!selectedOptions.containsAll(option.getRequiredOptions())) {
                it.remove();
            }
        }

        return modelMapper.map(availableOptions, new TypeToken<Set<BasicOptionDto>>() {}.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getBasket(int contractId) {
        Contract contract = contractDao.getByKey(contractId);

        return modelMapper.map(contract.getBasket(), new TypeToken<Set<BasicOptionDto>>() {}.getType());
    }

    // EDIT

    @Override
    @Transactional
    public void changeContractTariff(int contractId, int replacementTariffId) {
        Tariff tariff = tariffDao.getByKey(replacementTariffId);
        Contract contract = contractDao.getByKey(contractId);
        contract.setTariff(tariff);
        contractDao.update(contract);
    }

    @Override
    @Transactional
    public void addOptionToBasket(int contractId, int optionId) {
        Option option = optionDao.getByKey(optionId);
        Contract contract = contractDao.getByKey(contractId);

        if (contract.getOptions().contains(option)) {
            // Error
            return;
        }

        if (contract.getBasket().contains(option)) {
            // Error
            return;
        }

        if (!contract.getTariff().getOptions().contains(option)) {
            // Error
            return;
        }

        Set<Option> selectedOptions = new HashSet<>();
        selectedOptions.addAll(contract.getOptions());
        selectedOptions.addAll(contract.getBasket());

        for (Option incompatibleOptionOf : option.getIncompatibleOptionsOf()) {
            if (selectedOptions.contains(incompatibleOptionOf)) {
                //Error
                return;
            }
        }

        for (Option incompatibleOption : option.getIncompatibleOptions()) {
            if (selectedOptions.contains(incompatibleOption)) {
                //Error
                return;
            }
        }

        for (Option incompatibleOption : option.getRequiredOptions()) {
            if (!selectedOptions.contains(incompatibleOption)) {
                //Error
                return;
            }
        }

        contract.getBasket().add(option);
        contractDao.update(contract);
    }

    @Override
    @Transactional
    public void removeOptionFromBasket(int contractId, int optionId) {
        Option option = optionDao.getByKey(optionId);
        Contract contract = contractDao.getByKey(contractId);
        Set<Option> contractOptions = contract.getBasket();

        for (Option requiredOptionOf : option.getRequiredOptionsOf()) {
            if (contractOptions.contains(requiredOptionOf)){
                contract.getBasket().remove(requiredOptionOf);
                return;
            }
        }

        contract.getBasket().remove(option);
        contractDao.update(contract);
    }

    @Override
    @Transactional
    public void submitBasket(int contractId) {
        Contract contract = contractDao.getByKey(contractId);

        double sum = 0;
        for (Option option : contract.getBasket()) sum += option.getConnectionPrice();

        if (sum > contract.getBalance()) {
            // error
            return;
        }

        contract.getOptions().addAll(contract.getBasket());

        for (Option option : contract.getBasket()) {
            History history = new History();
            history.setDate(new Date());
            history.setName(option.getName());
            history.setPrice(option.getPrice());
            history.setContract(contract);
            historyDao.save(history);
        }

        contract.getBasket().clear();

        contractDao.update(contract);
    }

    @Override
    @Transactional
    public void clearBasket(int contractId) {
        Contract contract = contractDao.getByKey(contractId);

        contract.getBasket().clear();

        contractDao.update(contract);
    }

    @Override
    @Transactional
    public void changeBlockStatus(int requestedUserId, int contractId) {
        User user = userDao.getByKey(requestedUserId);
        Contract contract = contractDao.getByKey(contractId);

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
    @Transactional
    public void addMoney(int contractId, double amount) {
        Contract contract = contractDao.getByKey(contractId);

        if (amount <= 0) {
            // ERROR
            return;
        }

        contract.setBalance(contract.getBalance() + amount);

        contractDao.update(contract);

        History history = new History();
        history.setDate(new Date());
        history.setName("Fill up balance : ");
        history.setPrice(contract.getBalance());
        history.setContract(contract);
        historyDao.save(history);

    }
}
