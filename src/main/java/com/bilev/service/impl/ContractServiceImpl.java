package com.bilev.service.impl;

import com.bilev.dao.api.BlockDao;
import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.HistoryDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.HistoryDto;
import com.bilev.exception.AccessException;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;

import com.bilev.model.Block;
import com.bilev.model.Contract;
import com.bilev.model.History;
import com.bilev.model.Option;
import com.bilev.model.Tariff;
import com.bilev.model.User;
import com.bilev.service.api.ContractService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Queue;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    public ContractDto getContract(int contractId) throws NotFoundException {

        try {
            Contract contract = contract = contractDao.getByKey(contractId);

            for (Option option : contract.getOptions()) {
                option.setAvailableForRemove(true);
                for (Option requiredOptionOf : option.getRequiredOptionsOf()) {
                    if (contract.getOptions().contains(requiredOptionOf)) {
                        option.setAvailableForRemove(false);
                        break;
                    }
                }
            }

            return modelMapper.map(contract, ContractDto.class);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getAvailableOptionsForContract(int contractId) throws NotFoundException {

        Contract contract;
        try {
            contract = contractDao.getByKey(contractId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

        Set<Option> availableOptions = contract.getTariff().getOptions();

        Set<Option> selectedOptions = new HashSet<>();
        selectedOptions.addAll(contract.getOptions());
        selectedOptions.addAll(contract.getBasket());

        availableOptions.removeAll(selectedOptions);

        for (Option contractOption : selectedOptions) {
            availableOptions.removeAll(contractOption.getIncompatibleOptions());
            availableOptions.removeAll(contractOption.getIncompatibleOptionsOf());
        }

        for (Iterator<Option> it = availableOptions.iterator(); it.hasNext();) {
            Option option = it.next();
            if (!selectedOptions.containsAll(option.getRequiredOptions()) ) {
                it.remove();
            }
        }

        return modelMapper.map(availableOptions, new TypeToken<Set<BasicOptionDto>>() {}.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getBasket(int contractId) throws NotFoundException {
        try {
            Contract contract = contractDao.getByKey(contractId);

            return modelMapper.map(contract.getBasket(), new TypeToken<Set<BasicOptionDto>>() {}.getType());
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> getContractHistory(int contractId) throws NotFoundException {
        try {
            Contract contract = contractDao.getByKey(contractId);

            List<HistoryDto> history = new LinkedList<> (
                    modelMapper.map(contract.getHistories(), new TypeToken<Set<HistoryDto>>() {}.getType()));

            history.sort((o1,o2) -> o2.getDate().compareTo(o1.getDate()));

            return history;
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }
    }

    // EDIT

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void changeContractTariff(int contractId, int replacementTariffId) throws NotFoundException, UnableToUpdateException, AccessException {
        Tariff tariff;
        Contract contract;
        try {
            tariff = tariffDao.getByKey(replacementTariffId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Tariff not found", e);
        }
        try {
            contract = contractDao.getByKey(contractId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

        if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new AccessException("Access denied");

        if (!tariff.getValid()) throw new UnableToUpdateException("Tariff '" + tariff.getName() + "' is currently unavailable");

        try {
            if (contract.getTariff().equals(tariff)) {
                throw new UnableToUpdateException();
            }
            contract.setTariff(tariff);
            contract.getBasket().clear();
            contract.getOptions().clear();

            contractDao.update(contract);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to change tariff", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addOptionToBasket(int contractId, int optionId) throws NotFoundException, UnableToUpdateException, AccessException {
        Option option;
        Contract contract;
        try {
            option = optionDao.getByKey(optionId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Option not found", e);
        }
        try {
            contract = contractDao.getByKey(contractId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

        if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new AccessException("Access denied");

        try {
            if (contract.getOptions().contains(option))
                throw new UnableToUpdateException("Option's been already added");

            if (contract.getBasket().contains(option)) {
                return;
            }

            if (!contract.getTariff().getOptions().contains(option))
                throw new UnableToUpdateException("");

            Set<Option> selectedOptions = new HashSet<>();
            selectedOptions.addAll(contract.getOptions());
            selectedOptions.addAll(contract.getBasket());

            for (Option incompatibleOptionOf : option.getIncompatibleOptionsOf()) {
                if (selectedOptions.contains(incompatibleOptionOf))
                    throw new UnableToUpdateException("");
            }

            for (Option incompatibleOption : option.getIncompatibleOptions()) {
                if (selectedOptions.contains(incompatibleOption))
                    throw new UnableToUpdateException("");
            }

            for (Option incompatibleOption : option.getRequiredOptions()) {
                if (!selectedOptions.contains(incompatibleOption))
                    throw new UnableToUpdateException("");
            }

            contract.getBasket().add(option);
            contractDao.update(contract);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to add option to basket", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeOptionFromBasket(int contractId, int optionId) throws NotFoundException, UnableToUpdateException, AccessException {
        Option option;
        Contract contract;
        try {
            option = optionDao.getByKey(optionId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Option not found", e);
        }
        try {
            contract = contractDao.getByKey(contractId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

        if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new AccessException("Access denied");

        Queue<Option> removedOptions = new LinkedList<>();
        removedOptions.add(option);

        while (!removedOptions.isEmpty()) {
            Option removedOption = removedOptions.poll();
            contract.getBasket().remove(removedOption);

            for (Option requiredOptionOf : removedOption.getRequiredOptionsOf()) {
                if (contract.getBasket().contains(requiredOptionOf)){
                    removedOptions.add(requiredOptionOf);
                }
            }
        }

        try {
            contractDao.update(contract);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to remove option from basket", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeOptionFromContract(int contractId, int optionId) throws NotFoundException, UnableToUpdateException, AccessException {
        Option option;
        Contract contract;
        try {
            option = optionDao.getByKey(optionId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Option not found", e);
        }
        try {
            contract = contractDao.getByKey(contractId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

        if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new AccessException("Access denied");

        for (Option requiredOptionOf : option.getRequiredOptionsOf()) {
            if (contract.getOptions().contains(requiredOptionOf)) {
                throw new UnableToUpdateException("Unable to remove option (some options require it)");
            }
        }
        contract.getOptions().remove(option);
        contractDao.update(contract);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void submitBasket(int contractId) throws NotFoundException, UnableToUpdateException, UnableToSaveException, AccessException {
        Contract contract;
        try {
            contract = contractDao.getByKey(contractId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

        if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new AccessException("Access denied");

        double sum = 0;
        for (Option option : contract.getBasket()) sum += option.getConnectionPrice();

        if (sum > contract.getBalance()) {
            throw new UnableToUpdateException("Not enough money");
        }
        contract.setBalance(contract.getBalance() - sum);
        contract.getOptions().addAll(contract.getBasket());

        for (Option option : contract.getBasket()) {
            History history = new History();
            history.setDate(new Date());
            history.setName(option.getName());
            history.setPrice(option.getConnectionPrice());
            history.setContract(contract);
            historyDao.save(history);
        }

        contract.getBasket().clear();

        try {
            contractDao.update(contract);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to submit basket", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void clearBasket(int contractId)
            throws UnableToUpdateException, NotFoundException, AccessException {

        try {
            Contract contract = contractDao.getByKey(contractId);

            if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new AccessException("Access denied");

            contract.getBasket().clear();
            contractDao.update(contract);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to clear basket", e);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void changeBlockStatus(int requestedUserId, int contractId)
            throws NotFoundException, UnableToUpdateException, AccessException {
        User user;
        Contract contract;
        try {
            user = userDao.getByKey(requestedUserId);
        } catch (NotFoundException e) {
            throw new NotFoundException("User not found", e);
        }
        try {
            contract = contractDao.getByKey(contractId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

        try {
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
                            throw new AccessException("Access denied");
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
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to change block status", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addMoney(int contractId, double amount)
            throws UnableToUpdateException, UnableToSaveException, NotFoundException, AccessException {

        try {
            Contract contract = contractDao.getByKey(contractId);

            if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new AccessException("Access denied");

            if (amount <= 0)
                throw new UnableToUpdateException();

            contract.setBalance(contract.getBalance() + amount);

            contractDao.update(contract);

            History history = new History();
            history.setDate(new Date());
            history.setName("Fill up balance");
            history.setPrice(amount);
            history.setContract(contract);
            historyDao.save(history);

        } catch (UnableToSaveException e) {
            throw new UnableToSaveException("Unable to save operation", e);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to add money", e);
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }

    }


}
