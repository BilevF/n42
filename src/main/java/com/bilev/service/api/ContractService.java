package com.bilev.service.api;

import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.model.Contract;

import java.util.List;
import java.util.Set;

public interface ContractService {
    List<Contract> getUserContracts(int userId);

    // Get

    ContractDto getContract(int contractId);

    Set<BasicOptionDto> getAvailableOptionsForContract(int contractId);

    // Edit

    void changeContractTariff(int contractId, int replacementTariffId);

    void addOption(int contractId, int optionId);

    void removeOption(int contractId, int optionId);

    void changeBlockStatus(int requestedUserId, int contractId);

    void addMoney(int contractId, double amount);
}
