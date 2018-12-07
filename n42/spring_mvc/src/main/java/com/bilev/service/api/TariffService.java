package com.bilev.service.api;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToRemoveException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.exception.service.OperationFailed;

import java.util.Collection;
import java.util.Set;

public interface TariffService {

    // Get

    Collection<BasicTariffDto> getAllTariffs() throws OperationFailed;

    Collection<BasicTariffDto> getAvailableTariffs(int contractId) throws OperationFailed;

    Collection<BasicTariffDto> getAvailableTariffs() throws OperationFailed;

    TariffDto getTariff(int tariffId) throws OperationFailed;

    Set<BasicOptionDto> getTariffBasicOptions(int tariffId) throws OperationFailed;

    OptionDto getOption(int optionId) throws OperationFailed;

    // Edit

    int saveTariff(BasicTariffDto tariffDto) throws OperationFailed;

    void removeTariff(int tariffId) throws OperationFailed;

    void replaceTariff(int originalId, int replacementId) throws OperationFailed;

    void saveOption(BasicOptionDto optionDto) throws OperationFailed;

    void removeOption(int optionId) throws OperationFailed;

    void blockTariff(int tariffId) throws OperationFailed;

    void unblockTariff(int tariffId) throws OperationFailed;
}
