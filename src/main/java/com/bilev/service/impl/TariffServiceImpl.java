package com.bilev.service.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToRemoveException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;
import com.bilev.model.Contract;
import com.bilev.model.Option;
import com.bilev.model.Tariff;
import com.bilev.service.api.TariffService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service("tariffService")
public class TariffServiceImpl implements TariffService {

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BasicTariffDto> getAllTariffs() {
        List<Tariff> tariffs = tariffDao.getAllTariffs();

        return modelMapper.map(tariffs, new TypeToken<List<BasicTariffDto>>() {}.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BasicTariffDto> getAvailableTariffs(int contractId) throws NotFoundException {

        try {
            List<Tariff> tariffs = tariffDao.getAvailableTariffs();
            Contract contract = contract = contractDao.getByKey(contractId);
            tariffs.remove(contract.getTariff());

            return modelMapper.map(tariffs, new TypeToken<List<BasicTariffDto>>() {}.getType());
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BasicTariffDto> getAvailableTariffs() {
        List<Tariff> tariffs = tariffDao.getAvailableTariffs();

        return modelMapper.map(tariffs, new TypeToken<List<BasicTariffDto>>() {}.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public TariffDto getTariff(int tariffId) throws NotFoundException {
        try {
            Tariff tariff = tariff = tariffDao.getByKey(tariffId);
            return modelMapper.map(tariff, TariffDto.class);
        } catch (NotFoundException e) {
            throw new NotFoundException("Tariff not found", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<OptionDto> getTariffOptions(int tariffId) throws NotFoundException {
        try {
            Tariff tariff = tariffDao.getByKey(tariffId);

            return modelMapper.map(tariff.getOptions(), new TypeToken<Set<OptionDto>>() {}.getType());
        } catch (NotFoundException e) {
            throw new NotFoundException("Tariff not found", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BasicOptionDto> getBasicTariffOptions(int tariffId) throws NotFoundException {
        try {
            Tariff tariff = tariffDao.getByKey(tariffId);

            return modelMapper.map(tariff.getOptions(), new TypeToken<Set<BasicOptionDto>>() {}.getType());
        } catch (NotFoundException e) {
            throw new NotFoundException("Tariff not found", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OptionDto getOption(int optionId) throws NotFoundException {
        try {
            Option option = optionDao.getByKey(optionId);

            return modelMapper.map(option, OptionDto.class);
        } catch (NotFoundException e) {
            throw new NotFoundException("Option not found", e);
        }
    }

    //////////////////

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int saveTariff(BasicTariffDto tariffDto) throws UnableToSaveException {
        try {
            Tariff tariff = modelMapper.map(tariffDto, Tariff.class);
            tariffDao.saveOrUpdate(tariff);
            return tariff.getId();
        } catch (UnableToSaveException e) {
            throw new UnableToSaveException("Tariff name is not unique / fields are empty", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeTariff(int tariffId) throws NotFoundException, UnableToRemoveException {
        try {
            Tariff tariff = tariff = tariffDao.getByKey(tariffId);
            if (tariff.getContracts().size() != 0 || tariff.getValid()) {
                throw new UnableToRemoveException();
            }

            optionDao.removeAll(tariff.getOptions());
            tariffDao.delete(tariff);
        } catch (NotFoundException e) {
            throw new NotFoundException("Tariff not found", e);
        } catch (UnableToRemoveException e) {
            throw new UnableToRemoveException("Unable to remove(tariff has users)", e);
        }

    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void replaceTariff(int originalId, int replacementId) throws NotFoundException, UnableToUpdateException {

        try {
            Tariff origTariff = origTariff = tariffDao.getByKey(originalId);
            Tariff replTariff = tariffDao.getByKey(replacementId);

            if (origTariff.getValid()) {
                throw new UnableToUpdateException();
            }
            for (Contract contract : origTariff.getContracts()) {
                contract.setTariff(replTariff);
                contract.getOptions().clear();
                contract.getBasket().clear();
            }
            contractDao.updateAll(origTariff.getContracts());
        } catch (NotFoundException e) {
            throw new NotFoundException("Tariff not found", e);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Tariff is not blocked / ... ", e);
        }
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveOption(BasicOptionDto optionDto) throws UnableToSaveException {

        try {
            Option option = modelMapper.map(optionDto, Option.class);

            for (BasicOptionDto relatedOption : optionDto.getRelatedOptions()) {
//                if (option.getId() != null && option.getId().equals(relatedOption.getId())) {
//                    throw new UnableToSaveException();
//                }
                switch (relatedOption.getSelectedOptionType()) {
                    case REQUIRED:
                        option.getRequiredOptions().add(modelMapper.map(relatedOption, Option.class));
                        break;
                    case INCOMPATIBLE:
                        option.getIncompatibleOptions().add(modelMapper.map(relatedOption, Option.class));
                        break;
                }
            }
            optionDao.saveOrUpdate(option);
        } catch (UnableToSaveException e) {
            throw new UnableToSaveException("Option name is not unique / fields are empty", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeOption(int optionId) throws UnableToRemoveException, UnableToUpdateException, NotFoundException {
        try {
            Option option = optionDao.getByKey(optionId);

            List<Contract> contracts = contractDao.getAllContractsWithOption(optionId);

            for (Contract contract : contracts) {
                contract.getBasket().remove(option);
                contract.getOptions().remove(option);
                contractDao.update(contract);
            }

            optionDao.delete(option);
        } catch (UnableToRemoveException e) {
            throw new UnableToRemoveException("Unable to remove option", e);//TODO
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to remove option", e);
        } catch (NotFoundException e) {
            throw new NotFoundException("Option not found", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void changeTariffStatus(int tariffId) throws NotFoundException, UnableToUpdateException {

        try {
            Tariff tariff = tariffDao.getByKey(tariffId);

            tariff.setValid(!tariff.getValid());
            tariffDao.update(tariff);
        } catch (UnableToUpdateException e) {
            throw new UnableToUpdateException("Unable to change tariff status", e);
        } catch (NotFoundException e) {
            throw new NotFoundException("Tariff not found", e);
        }
    }

}
