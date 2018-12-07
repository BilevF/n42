package com.bilev.controller.rest;


import com.bilev.dto.BasicContractDto;

import com.bilev.dto.UserDto;

import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.ContractService;
import com.bilev.service.api.SecurityService;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/rest/contract")
public class ContractRestController {

    private final ContractService contractService;

    private final UserService userService;

    private final SecurityService securityService;

    @Autowired
    public ContractRestController(ContractService contractService, UserService userService, SecurityService securityService) {
        this.contractService = contractService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping
    public int addContract(@RequestBody @Valid BasicContractDto contract) throws OperationFailed {

        return contractService.saveContract(contract);
    }


    @DeleteMapping(value = "/option")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void removeOption(@RequestParam("contractId") Integer contractId,
                             @RequestParam("optionId") Integer optionId) throws OperationFailed {

        contractService.removeOptionFromContract(contractId, optionId);
    }


    @PutMapping(value = "/basket/option")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void addToBasket(@RequestParam("contractId") Integer contractId,
                            @RequestParam("optionId") Integer optionId) throws OperationFailed {

        contractService.addOptionToBasket(contractId, optionId);
    }


    @DeleteMapping(value = "/basket/option")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void removeFromBasket(@RequestParam("contractId") Integer contractId,
                                 @RequestParam("optionId") Integer optionId) throws OperationFailed {

        contractService.removeOptionFromBasket(contractId, optionId);
    }


    @DeleteMapping(value = "/basket")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void clearBasket(@RequestParam("contractId") Integer contractId) throws OperationFailed {

        contractService.clearBasket(contractId);
    }


    @PatchMapping(value = "/basket/submit")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void submitBasket(@RequestParam("contractId") Integer contractId) throws OperationFailed {

        contractService.submitBasket(contractId);
    }


    @PatchMapping(value = "/tariff")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void changeTariffAction(@RequestParam("contractId") Integer contractId,
                                   @RequestParam("tariffId") Integer tariffId) throws OperationFailed {

        contractService.changeContractTariff(contractId, tariffId);
    }


    @PatchMapping(value = "/block")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void blockContract(@RequestParam("contractId") Integer contractId, Principal principal)
            throws OperationFailed {

        UserDto user = userService.getUserByEmail(principal.getName());
        contractService.blockContract(user.getId(), contractId);
    }

    @PatchMapping(value = "/unblock")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void unblockContract(@RequestParam("contractId") Integer contractId, Principal principal)
            throws OperationFailed {

        UserDto user = userService.getUserByEmail(principal.getName());
        contractService.unblockContract(user.getId(), contractId);
    }

    @PatchMapping(value = "/money")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public void addMoney(@RequestParam("contractId") Integer contractId,
                                 @RequestParam("moneyValue") Double moneyValue) throws OperationFailed {

        contractService.addMoney(contractId, moneyValue);
    }

}
