package com.bilev.tools;

import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.TariffDto;
import com.bilev.model.Tariff;
import org.springframework.stereotype.Service;

@Service
public class TariffCreator implements Creator<TariffDto, BasicTariffDto, Tariff> {

    private final String name = "test";

    private final Double price = 0.0;

    private final String info = "info";

    private final Boolean valid = true;


    @Override
    public TariffDto getDto(int id) {
        TariffDto tariff = new TariffDto();

        tariff.setId(id);
        tariff.setName(id + name);
        tariff.setPrice(price);
        tariff.setInfo(info);
        tariff.setValid(valid);

        return tariff;
    }

    @Override
    public BasicTariffDto getBasicDto(int id) {
        BasicTariffDto tariff = new BasicTariffDto();

        tariff.setId(id);
        tariff.setName(id + name);
        tariff.setPrice(price);
        tariff.setInfo(info);
        tariff.setValid(valid);

        return tariff;
    }

    @Override
    public Tariff getEntity(int id) {
        Tariff tariff = new Tariff();

        tariff.setId(id);
        tariff.setName(id + name);
        tariff.setPrice(price);
        tariff.setInfo(info);
        tariff.setValid(valid);

        return tariff;
    }
}
