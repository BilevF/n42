package com.bilev.service.api;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.HistoryDto;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;

import java.util.Collection;
import java.util.Set;


public interface ContractService {
    // Get

    ContractDto getContract(int contractId) throws NotFoundException;

    Set<BasicOptionDto> getAvailableOptionsForContract(int contractId) throws NotFoundException;

    Set<BasicOptionDto> getBasket(int contractId) throws NotFoundException;

    Collection<HistoryDto> getContractHistory(int contractId) throws NotFoundException;

    // Edit

    void changeContractTariff(int contractId, int replacementTariffId) throws NotFoundException, UnableToUpdateException;

    void addOptionToBasket(int contractId, int optionId) throws NotFoundException, UnableToUpdateException;

    void removeOptionFromBasket(int contractId, int optionId) throws NotFoundException, UnableToUpdateException;

    void changeBlockStatus(int requestedUserId, int contractId) throws NotFoundException, UnableToUpdateException;

    void submitBasket(int contractId) throws NotFoundException, UnableToUpdateException, UnableToSaveException;

    void clearBasket(int contractId) throws UnableToUpdateException, NotFoundException;

    void addMoney(int contractId, double amount) throws UnableToUpdateException, UnableToSaveException, NotFoundException;
}
