package com.bilev.service.api;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;

import java.util.Collection;


public interface ContractService {
    // Get

    ContractDto getContract(int contractId);

    Collection<BasicOptionDto> getAvailableOptionsForContract(int contractId);

    Collection<BasicOptionDto> getBasket(int contractId);

    // Edit

    void changeContractTariff(int contractId, int replacementTariffId);

    void addOptionToBasket(int contractId, int optionId);

    void removeOptionFromBasket(int contractId, int optionId);

    void changeBlockStatus(int requestedUserId, int contractId);

    void submitBasket(int contractId);

    void clearBasket(int contractId);

    void addMoney(int contractId, double amount);
}
