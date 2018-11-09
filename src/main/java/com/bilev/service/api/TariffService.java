package com.bilev.service.api;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToRemoveException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TariffService {

    // Get

    Collection<BasicTariffDto> getAllTariffs();

    Collection<BasicTariffDto> getAvailableTariffs(int contractId) throws NotFoundException;

    Collection<BasicTariffDto> getAvailableTariffs();

    TariffDto getTariff(int tariffId) throws NotFoundException;

    Set<OptionDto> getTariffOptions(int tariffId) throws NotFoundException;

    Set<BasicOptionDto> getBasicTariffOptions(int tariffId) throws NotFoundException;

    OptionDto getOption(int optionId) throws NotFoundException;

    // Edit

    int saveTariff(BasicTariffDto tariffDto) throws UnableToSaveException;

    void removeTariff(int tariffId) throws NotFoundException, UnableToRemoveException;

    void replaceTariff(int originalId, int replacementId) throws NotFoundException, UnableToUpdateException;

    void saveOption(BasicOptionDto optionDto) throws UnableToSaveException;

    void removeOption(int optionId) throws UnableToRemoveException, UnableToUpdateException, NotFoundException;

    void changeTariffStatus(int tariffId) throws NotFoundException, UnableToUpdateException;
}
