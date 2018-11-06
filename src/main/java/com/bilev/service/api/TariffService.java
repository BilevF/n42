package com.bilev.service.api;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TariffService {

    // Get

    Collection<BasicTariffDto> getAllTariffs();

    TariffDto getTariff(int tariffId);

    Set<OptionDto> getTariffOptions(int tariffId);

    Set<BasicOptionDto> getBasicTariffOptions(int tariffId);

    OptionDto getOption(int optionId);

    // Edit

    int saveTariff(BasicTariffDto tariffDto);

    void removeTariff(int tariffId);

    void replaceTariff(int originalId, int replacementId);

    void saveOption(BasicOptionDto optionDto);

    void removeOption(int optionId);
}
