package com.bilev.service.api;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.HistoryDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.exception.service.OperationFailed;

import java.util.Collection;
import java.util.Set;


public interface ContractService {
    // Get

    ContractDto getContract(int contractId) throws OperationFailed;

    Set<BasicOptionDto> getAvailableOptionsForContract(int contractId) throws OperationFailed;

    Set<BasicOptionDto> getBasket(int contractId) throws OperationFailed;

    Collection<HistoryDto> getContractHistory(int contractId) throws OperationFailed;

    // Edit

    int saveContract(BasicContractDto contractDto) throws OperationFailed;

    void changeContractTariff(int contractId, int replacementTariffId) throws OperationFailed;

    void addOptionToBasket(int contractId, int optionId) throws OperationFailed;

    void removeOptionFromBasket(int contractId, int optionId) throws OperationFailed;

    void removeOptionFromContract(int contractId, int optionId) throws OperationFailed;

    void blockContract(int requestedUserId, int contractId) throws OperationFailed;

    void unblockContract(int requestedUserId, int contractId) throws OperationFailed;

    void submitBasket(int contractId) throws OperationFailed;

    void clearBasket(int contractId) throws OperationFailed;

    void addMoney(int contractId, double amount) throws OperationFailed;
}
