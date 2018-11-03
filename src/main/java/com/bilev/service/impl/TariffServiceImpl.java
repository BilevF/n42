package com.bilev.service.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.model.Contract;
import com.bilev.model.Option;
import com.bilev.model.Tariff;
import com.bilev.service.api.TariffService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<TariffDto> getAllTariffs() {
        List<Tariff> tariffs = tariffDao.getAllTariffs();

        return modelMapper.map(tariffs, new TypeToken<List<TariffDto>>() {}.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public TariffDto getTariff(int tariffId) {
        Tariff tariff = tariffDao.findById(tariffId);

        return modelMapper.map(tariff, TariffDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OptionDto> getTariffOptions(int tariffId) {
        Tariff tariff = tariffDao.findById(tariffId);

        return modelMapper.map(tariff.getOptions(), new TypeToken<List<OptionDto>>() {}.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public OptionDto getOption(int optionId) {
        Option option = optionDao.findById(optionId);

        return modelMapper.map(option, OptionDto.class);
    }

    //////////////////

    @Override
    @Transactional
    public int saveTariff(TariffDto tariffDto) {
        Tariff tariff = modelMapper.map(tariffDto, Tariff.class);

        tariffDao.saveOrUpdate(tariff);
        return tariff.getId();
    }

    @Override
    @Transactional
    public void removeTariff(int tariffId) {
        Tariff tariff = tariffDao.findById(tariffId);
        if (tariff.getContracts().size() != 0 || tariff.getValid()) {
            // Error
            return;
        }

        optionDao.removeAll(tariff.getOptions());
        tariffDao.remove(tariff);
    }

    @Override
    @Transactional
    public void replaceTariff(int originalId, int replacementId) {
        Tariff origTariff = tariffDao.findById(originalId);
        Tariff replTariff = tariffDao.findById(replacementId);

        if (origTariff.getValid()) {
            // Error
            return;
        }
        for (Contract contract : origTariff.getContracts()) {
            contract.setTariff(replTariff);
            contract.getOptions().clear();
        }
        contractDao.updateAll(origTariff.getContracts());
    }


    @Override
    @Transactional
    public void saveOption(BasicOptionDto optionDto, List<BasicOptionDto> relatedOptions) {
        Option option = modelMapper.map(optionDto, Option.class);

        for (BasicOptionDto relatedOption : relatedOptions) {
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
    }

    @Override
    @Transactional
    public void removeOption(int optionId) {
        Option option = optionDao.findById(optionId);
        optionDao.remove(option);
    }

}
