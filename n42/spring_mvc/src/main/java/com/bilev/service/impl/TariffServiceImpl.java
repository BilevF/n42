package com.bilev.service.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToRemoveException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
import com.bilev.model.Contract;
import com.bilev.model.Option;
import com.bilev.model.Tariff;
import com.bilev.service.api.TariffService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Collection;

import java.util.List;
import java.util.Set;

@Service("tariffService")
public class TariffServiceImpl implements TariffService, ServiceErrors {

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;


    private TariffDto getTariffDto (Tariff tariff) {
        TariffDto tariffDto = new TariffDto();

        tariffDto.setId(tariff.getId());
        tariffDto.setInfo(tariff.getInfo());
        tariffDto.setName(tariff.getName());
        tariffDto.setPrice(tariff.getPrice());
        tariffDto.setValid(tariff.getValid());

        for (Option option : tariff.getOptions()) {
            tariffDto.getOptions().add(modelMapper.map(option, OptionDto.class));
        }
        return tariffDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BasicTariffDto> getAllTariffs() throws OperationFailed {
        try {
            return modelMapper.map(
                    tariffDao.getAllTariffs(),
                    new TypeToken<List<BasicTariffDto>>() {}.getType());
        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BasicTariffDto> getAvailableTariffs(int contractId) throws OperationFailed {

        try {
            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) throw new OperationFailed(CONTRACT_NOT_FOUND);

            List<Tariff> tariffs = tariffDao.getAvailableTariffs();
            tariffs.remove(contract.getTariff());

            return modelMapper.map(
                    tariffs,
                    new TypeToken<List<BasicTariffDto>>() {}.getType());

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BasicTariffDto> getAvailableTariffs() throws OperationFailed {
        try {
            return modelMapper.map(
                    tariffDao.getAvailableTariffs(),
                    new TypeToken<List<BasicTariffDto>>() {}.getType());
        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TariffDto getTariff(int tariffId) throws OperationFailed {
        try {
            Tariff tariff = tariffDao.getByKey(tariffId);
            if (tariff == null) throw new OperationFailed(TARIFF_NOT_FOUND);

            return getTariffDto(tariff);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getTariffBasicOptions(int tariffId) throws OperationFailed {
        try {
            Tariff tariff = tariffDao.getByKey(tariffId);
            if (tariff == null) throw new OperationFailed(TARIFF_NOT_FOUND);

            return modelMapper.map(
                    tariff.getOptions(),
                    new TypeToken<Set<BasicOptionDto>>() {}.getType());

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OptionDto getOption(int optionId) throws OperationFailed {
        try {
            Option option = optionDao.getByKey(optionId);
            if (option == null) throw new OperationFailed(OPTION_NOT_FOUND);

            return modelMapper.map(
                    option,
                    OptionDto.class);

        } catch (UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_FIND);
        }
    }

    //////////////////

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int saveTariff(@Valid BasicTariffDto basicTariffDto) throws OperationFailed {
        try {

            if (!validator.validate(basicTariffDto).isEmpty()) throw new OperationFailed(VALIDATION);

            Tariff hasTariff = tariffDao.getTariffByName(basicTariffDto.getName());
            if (hasTariff != null) throw new OperationFailed(TARIFF_NAME_NOT_UNIQUE);

            Tariff tariff = modelMapper.map(
                    basicTariffDto,
                    Tariff.class);

            tariffDao.persist(tariff);

            return tariff.getId();

        } catch (UnableToSaveException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_SAVE);
        }
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeTariff(int tariffId) throws OperationFailed {
        try {
            Tariff tariff = tariffDao.getByKey(tariffId);
            if (tariff == null) throw new OperationFailed(TARIFF_NOT_FOUND);

            if (tariff.getValid()) throw new OperationFailed(TARIFF_VALID);

            if (tariff.getContracts().size() != 0) throw new OperationFailed(TARIFF_HAS_USERS);

            optionDao.removeAll(tariff.getOptions());

            tariffDao.delete(tariff);

        } catch (UnableToRemoveException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_REMOVE);
        }

    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void replaceTariff(int originalId, int replacementId) throws OperationFailed {

        try {
            if (originalId == replacementId) throw new OperationFailed(UNABLE_TO_UPDATE);

            Tariff originalTariff = tariffDao.getByKey(originalId);
            Tariff replacementTariff = tariffDao.getByKey(replacementId);

            if (originalTariff == null || replacementTariff == null)
                throw new OperationFailed(TARIFF_NOT_FOUND);

            if (originalTariff.getValid()) throw new OperationFailed(TARIFF_VALID);
            if (!replacementTariff.getValid()) throw new OperationFailed(TARIFF_UNAVAILABLE);

            for (Contract contract : originalTariff.getContracts()) {
                contract.setTariff(replacementTariff);
                contract.getOptions().clear();
                contract.getBasket().clear();
            }

            contractDao.updateAll(originalTariff.getContracts());

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_UPDATE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveOption(@Valid BasicOptionDto basicOptionDto) throws OperationFailed {

        try {

            if (!validator.validate(basicOptionDto).isEmpty()) throw new OperationFailed(VALIDATION);

            Option option = modelMapper.map(
                    basicOptionDto,
                    Option.class);

            for (BasicOptionDto relatedOption : basicOptionDto.getRelatedOptions()) {
                Option optionEntity = optionDao.getByKey(relatedOption.getId());
                if (optionEntity == null) throw new OperationFailed(RELATED_OPTION_REMOVED);

                switch (relatedOption.getSelectedOptionType()) {
                    case REQUIRED:
                        option.getRequiredOptions().add(optionEntity);
                        break;
                    case INCOMPATIBLE:
                        option.getIncompatibleOptions().add(optionEntity);
                        break;
                }
            }

            optionDao.persist(option);

        } catch (UnableToSaveException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_SAVE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeOption(int optionId) throws OperationFailed {

        try {

            Option option = optionDao.getByKey(optionId);
            if (option == null) throw new OperationFailed(OPTION_NOT_FOUND);

            List<Contract> contracts = contractDao.getAllContractsWithOption(optionId);

            for (Contract contract : contracts) {
                contract.getBasket().remove(option);
                contract.getOptions().remove(option);
                contractDao.update(contract);
            }

            optionDao.delete(option);

        } catch (UnableToRemoveException | UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_REMOVE);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void blockTariff(int tariffId) throws OperationFailed {

        try {

            Tariff tariff = tariffDao.getByKey(tariffId);
            if (tariff == null) throw new OperationFailed(TARIFF_NOT_FOUND);

            tariff.setValid(false);

            tariffDao.update(tariff);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_EDIT);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void unblockTariff(int tariffId) throws OperationFailed {

        try {

            Tariff tariff = tariffDao.getByKey(tariffId);
            if (tariff == null) throw new OperationFailed(TARIFF_NOT_FOUND);

            tariff.setValid(true);

            tariffDao.update(tariff);

        } catch (UnableToUpdateException | UnableToFindException e) {
            throw new OperationFailed(UNABLE_TO_EDIT);
        }
    }

}
