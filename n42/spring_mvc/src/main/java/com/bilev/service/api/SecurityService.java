package com.bilev.service.api;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.MoneyTransferDto;
import com.bilev.exception.service.OperationFailed;


public interface SecurityService {

    boolean hasAccessToContract(int contractId);

    boolean hasAccessToContract(BasicContractDto contract);

    boolean hasAccessToUser(int userId);

    boolean hasAccessToUser(BasicUserDto user);

    boolean isTrustedTransaction(MoneyTransferDto transferDto);

    boolean isValidMailToken(int userId, String token);

    String getMailToken(int userId) throws OperationFailed;
}
