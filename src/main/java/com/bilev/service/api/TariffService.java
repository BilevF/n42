package com.bilev.service.api;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;

import java.util.List;

public interface TariffService {

    // Get

    List<TariffDto> getAllTariffs();

    TariffDto getTariff(int tariffId);

    List<OptionDto> getTariffOptions(int tariffId);

    OptionDto getOption(int optionId);

    // Edit

    int saveTariff(TariffDto tariffDto);

    void removeTariff(int tariffId);

    void replaceTariff(int originalId, int replacementId);

    void saveOption(BasicOptionDto optionDto, List<BasicOptionDto> relatedOptions);

    void removeOption(int optionId);
}
