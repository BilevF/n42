package com.bilev.service.impl;

import com.bilev.dao.api.BlockDao;
import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.HistoryDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.HistoryDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;

import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
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

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import java.util.Queue;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service("contractService")
public class ContractServiceImpl implements ContractService, ServiceErrors {

    private final ContractDao contractDao;

    private final TariffDao tariffDao;

    private final OptionDao optionDao;

    private final UserDao userDao;

    private final BlockDao blockDao;

    private final HistoryDao historyDao;

    private final ModelMapper modelMapper;

    private final Validator validator;

    @Autowired
    public ContractServiceImpl(ContractDao contractDao, TariffDao tariffDao, OptionDao optionDao, UserDao userDao,
                               BlockDao blockDao, HistoryDao historyDao, ModelMapper modelMapper, Validator validator) {
        this.contractDao = contractDao;
        this.tariffDao = tariffDao;
        this.optionDao = optionDao;
        this.userDao = userDao;
        this.blockDao = blockDao;
        this.historyDao = historyDao;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    // Get

    @Override
    @Transactional(readOnly = true)
    public ContractDto getContract(int contractId) throws OperationFailed {

        try {
            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

            for (Option option : contract.getOptions()) {

                option.setAvailableForRemove(true);

                for (Option requiredOptionOf : option.getRequiredOptionsOf()) {
                    if (contract.getOptions().contains(requiredOptionOf)) {
                        option.setAvailableForRemove(false);
                        break;
                    }
                }
            }

            return modelMapper.map(
                    contract,
                    ContractDto.class);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getAvailableOptionsForContract(int contractId) throws OperationFailed {

        try {

            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

            Set<Option> availableOptions = contract.getTariff().getOptions();

            Set<Option> selectedOptions = new HashSet<>();
            selectedOptions.addAll(contract.getOptions());
            selectedOptions.addAll(contract.getBasket());

            availableOptions.removeAll(selectedOptions);

            for (Option contractOption : selectedOptions) {
                availableOptions.removeAll(contractOption.getIncompatibleOptions());
                availableOptions.removeAll(contractOption.getIncompatibleOptionsOf());
            }

            availableOptions.removeIf(option -> !selectedOptions.containsAll(option.getRequiredOptions()));

            return modelMapper.map(
                    availableOptions,
                    new TypeToken<Set<BasicOptionDto>>() {}.getType());

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getBasket(int contractId) throws OperationFailed {
        try {
            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

            return modelMapper.map(
                    contract.getBasket(),
                    new TypeToken<Set<BasicOptionDto>>() {}.getType());

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> getContractHistory(int contractId) throws OperationFailed {
        try {

            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

            List<HistoryDto> history = new LinkedList<> (
                    modelMapper.map(
                        contract.getHistories(),
                        new TypeToken<Set<HistoryDto>>() {}.getType()));

            history.sort((o1,o2) -> o2.getDate().compareTo(o1.getDate()));

            return history;

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }
//
    // EDIT

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int saveContract(@Valid BasicContractDto basicContractDto) throws OperationFailed {

        try {

            if (!validator.validate(basicContractDto).isEmpty()) throw new OperationFailed(VALIDATION);

            Contract hasContract = contractDao.getContractByPhone(basicContractDto.getPhoneNumber());
            if (hasContract != null) throw new OperationFailed(PHONE_NOT_UNIQUE);

            Block block = blockDao.getBlockByType(Block.BlockType.NON);
            if (block == null) throw new OperationFailed(UNABLE_TO_SAVE);

            Tariff tariff = tariffDao.getByKey(basicContractDto.getTariff().getId());
            if (tariff == null) throw new OperationFailed(TARIFF_NOT_FOUND);

            Contract contract = modelMapper.map(
                    basicContractDto,
                    Contract.class);

            contract.setBalance(0.0);

            contract.setBlock(block);

            contract.setTariff(tariff);

            contractDao.persist(contract);

            return contract.getId();

        } catch (UnableToFindException | UnableToSaveException e) {
            throw new OperationFailed(UNABLE_TO_SAVE);
        }
    }

    private Contract getNonBlockContract(int contractId) throws OperationFailed, UnableToFindException {
        Contract contract = contractDao.getByKey(contractId);
        if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

        if (contract.getBlock().getBlockType() != Block.BlockType.NON) throw new OperationFailed(ACCESS_DENIED);

        return contract;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void changeContractTariff(int contractId, int replacementTariffId) throws OperationFailed {

        try {

            Tariff tariff = tariffDao.getByKey(replacementTariffId);
            if (tariff == null) throw new OperationFailed(TARIFF_NOT_FOUND);

            Contract contract = getNonBlockContract(contractId);

            if (!tariff.getValid()) throw new OperationFailed(TARIFF_UNAVAILABLE);

            if (contract.getTariff().equals(tariff)) throw new OperationFailed(UNABLE_TO_UPDATE);

            contract.setTariff(tariff);
            contract.getBasket().clear();
            contract.getOptions().clear();

            contractDao.update(contract);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addOptionToBasket(int contractId, int optionId) throws OperationFailed {

        try {

            Option option = optionDao.getByKey(optionId);
            if (option == null) throw new OperationFailed(OPTION_NOT_FOUND);

            Contract contract = getNonBlockContract(contractId);

            if (contract.getOptions().contains(option)) throw new OperationFailed(OPTION_ALREADY_ADDED);

            if (contract.getBasket().contains(option)) return;

            if (!contract.getTariff().getOptions().contains(option)) throw new OperationFailed(UNABLE_TO_UPDATE);

            Set<Option> selectedOptions = new HashSet<>();
            selectedOptions.addAll(contract.getOptions());
            selectedOptions.addAll(contract.getBasket());

            for (Option incompatibleOptionOf : option.getIncompatibleOptionsOf()) {
                if (selectedOptions.contains(incompatibleOptionOf))
                    throw new OperationFailed(UNABLE_TO_UPDATE);
            }

            for (Option incompatibleOption : option.getIncompatibleOptions()) {
                if (selectedOptions.contains(incompatibleOption))
                    throw new OperationFailed(UNABLE_TO_UPDATE);
            }

            for (Option requiredOption : option.getRequiredOptions()) {
                if (!selectedOptions.contains(requiredOption))
                    throw new OperationFailed(UNABLE_TO_UPDATE);
            }

            contract.getBasket().add(option);

            contractDao.update(contract);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeOptionFromBasket(int contractId, int optionId) throws OperationFailed {

        try {

            Option option = optionDao.getByKey(optionId);
            if (option == null) throw new OperationFailed(OPTION_NOT_FOUND);

            Contract contract = getNonBlockContract(contractId);

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

            contractDao.update(contract);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_REMOVE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeOptionFromContract(int contractId, int optionId) throws OperationFailed {

        try {
            Option option = optionDao.getByKey(optionId);
            if (option == null) throw new OperationFailed(OPTION_NOT_FOUND);

            Contract contract = getNonBlockContract(contractId);

            for (Option requiredOptionOf : option.getRequiredOptionsOf()) {
                if (contract.getOptions().contains(requiredOptionOf)) {
                    throw new OperationFailed(UNABLE_TO_REMOVE);
                }
            }
            contract.getOptions().remove(option);
            contractDao.update(contract);

        } catch (UnableToFindException | UnableToUpdateException e) {
            throw new OperationFailed(UNABLE_TO_REMOVE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void submitBasket(int contractId) throws OperationFailed {
        try {
            Contract contract = getNonBlockContract(contractId);

            double sum = 0;
            for (Option option : contract.getBasket()) sum += option.getConnectionPrice();

            if (sum > contract.getBalance()) throw new OperationFailed(NOT_ENOUGH_MONEY);

            contract.setBalance(contract.getBalance() - sum);
            contract.getOptions().addAll(contract.getBasket());

            for (Option option : contract.getBasket()) {
                History history = new History();
                history.setDate(new Date());
                history.setName(option.getName());
                history.setPrice(option.getConnectionPrice());
                history.setContract(contract);
                historyDao.persist(history);
            }

            contract.getBasket().clear();

            contractDao.update(contract);

        } catch (UnableToUpdateException | UnableToFindException | UnableToSaveException e) {
            throw new OperationFailed(UNABLE_TO_SUBMIT);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void clearBasket(int contractId) throws OperationFailed {

        try {
            Contract contract = getNonBlockContract(contractId);

            contract.getBasket().clear();

            contractDao.update(contract);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void blockContract(int requestedUserId, int contractId) throws OperationFailed {

        try {

            User user = userDao.getByKey(requestedUserId);

            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

            Block adminBlock = blockDao.getBlockByType(Block.BlockType.ADMIN_BLOCK);
            Block clientBlock = blockDao.getBlockByType(Block.BlockType.CLIENT_BLOCK);
            if (adminBlock == null || clientBlock == null) throw new OperationFailed(UNABLE_TO_UPDATE);

            if (contract.getBlock().getBlockType() != Block.BlockType.NON) return;

            switch (user.getRole().getRoleName()) {
                case ROLE_ADMIN:
                    contract.setBlock(adminBlock);
                    break;
                case ROLE_CLIENT:
                    contract.setBlock(clientBlock);
                    break;
            }

            contractDao.update(contract);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void unblockContract(int requestedUserId, int contractId) throws OperationFailed {

        try {

            User user = userDao.getByKey(requestedUserId);

            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

            Block block = blockDao.getBlockByType(Block.BlockType.NON);
            if (block == null) throw new OperationFailed(UNABLE_TO_UPDATE);

            if (contract.getBlock().getBlockType() == Block.BlockType.NON) return;

            switch (user.getRole().getRoleName()) {
                case ROLE_ADMIN:
                    contract.setBlock(block);
                    break;
                case ROLE_CLIENT:
                    switch (contract.getBlock().getBlockType()) {
                        case ADMIN_BLOCK:
                            throw new OperationFailed(ACCESS_DENIED);
                        case CLIENT_BLOCK:
                            contract.setBlock(block);
                            break;
                    }
                    break;
            }

            contractDao.update(contract);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addMoney(int contractId, double amount) throws OperationFailed {

        try {
            Contract contract = getNonBlockContract(contractId);

            if (amount < 0) throw new OperationFailed(UNABLE_TO_UPDATE);

            contract.setBalance(contract.getBalance() + amount);

            contractDao.update(contract);

            History history = new History();
            history.setDate(new Date());
            history.setName("Fill up balance");
            history.setPrice(amount);
            history.setContract(contract);
            historyDao.persist(history);

        } catch (UnableToUpdateException | UnableToSaveException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }

}
