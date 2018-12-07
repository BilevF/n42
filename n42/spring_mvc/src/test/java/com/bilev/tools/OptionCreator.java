package com.bilev.tools;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.OptionDto;
import com.bilev.model.Option;
import org.springframework.stereotype.Service;

@Service
public class OptionCreator implements Creator<OptionDto, BasicOptionDto, Option> {

    private final String name = "test";;

    private final Double price = 0.0;

    private final Double connectionPrice = 0.0;

    private final String info = "info";

    private final int tariffId = 0;

    @Override
    public OptionDto getDto(int id) {
        OptionDto option = new OptionDto();

        option.setId(id);
        option.setName(id + name);
        option.setPrice(price);
        option.setInfo(info);
        option.setConnectionPrice(connectionPrice);
        option.setTariffId(tariffId);

        return option;
    }

    @Override
    public BasicOptionDto getBasicDto(int id) {
        BasicOptionDto option = new BasicOptionDto();

        option.setId(id);
        option.setName(id + name);
        option.setPrice(price);
        option.setInfo(info);
        option.setConnectionPrice(connectionPrice);
        option.setTariffId(tariffId);

        return option;
    }

    @Override
    public Option getEntity(int id) {
        Option option = new Option();

        option.setId(id);
        option.setName(id + name);
        option.setPrice(price);
        option.setInfo(info);
        option.setConnectionPrice(connectionPrice);

        return option;
    }
}
