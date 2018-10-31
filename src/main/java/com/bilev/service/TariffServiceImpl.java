package com.bilev.service;

import com.bilev.dao.OptionDao;
import com.bilev.dao.TariffDao;
import com.bilev.model.Option;
import com.bilev.model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("tariffService")
@Transactional
public class TariffServiceImpl implements TariffService {

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionDao optionDao;

    @Override
    public void saveTariff(Tariff tariff) {
        tariffDao.saveTariff(tariff);
    }

    @Override
    public List<Tariff> getAllTariffs() {
        List<Tariff> listOftTariffs = tariffDao.getAllTariffs();
        return listOftTariffs;
    }

    @Override
    public Tariff findById(int id) {
        Tariff tariff = tariffDao.findById(id);
        tariff.getOptions().size();
        return tariff;
    }

    @Override
    public void saveOption(Option option) {
        for (Option tariffOption : option.getTariff().getOptions()) {
            switch (tariffOption.getSelectedType()) {
                case REQUIRED:
                    option.getRequiredOptions().add(tariffOption);
                    break;
                case INCOMPATIBLE:
                    option.getIncompatibleOptions().add(tariffOption);
                    break;
            }
        }
        optionDao.save(option);
    }

    @Override
    public Option findOption(int id) {
        return optionDao.findById(id);
    }

    @Override
    public void removeOption(int id) {
        
    }


}
