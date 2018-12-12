package com.bilev.controller.rest;


import com.bilev.dto.BasicContractDto;

import com.bilev.dto.UserDto;

import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.ContractService;
import com.bilev.service.api.SecurityService;
import com.bilev.service.api.UserService;
import com.bilev.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;


@RestController
@RequestMapping("/rest/contract")
public class ContractRestController {

    private final ContractService contractService;

    @Autowired
    public ContractRestController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public int addContract(@RequestBody @Valid BasicContractDto contract) throws OperationFailed {

        return contractService.saveContract(contract);
    }

    @PatchMapping
    @PreAuthorize("@securityService.hasAccessToContract(#contract)")
    public void updateContract(@RequestBody @Valid BasicContractDto contract) throws OperationFailed {

        contractService.updateContract(contract);
    }


    @DeleteMapping(value = "/option")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void removeOption(@RequestParam("contractId") Integer contractId,
                             @RequestParam("optionId") Integer optionId) throws OperationFailed {

        contractService.removeOptionFromContract(contractId, optionId);
    }


    @PutMapping(value = "/basket/option")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void addToBasket(@RequestParam("contractId") Integer contractId,
                            @RequestParam("optionId") Integer optionId) throws OperationFailed {

        contractService.addOptionToBasket(contractId, optionId);
    }


    @DeleteMapping(value = "/basket/option")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void removeFromBasket(@RequestParam("contractId") Integer contractId,
                                 @RequestParam("optionId") Integer optionId) throws OperationFailed {

        contractService.removeOptionFromBasket(contractId, optionId);
    }


    @DeleteMapping(value = "/basket")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void clearBasket(@RequestParam("contractId") Integer contractId) throws OperationFailed {

        contractService.clearBasket(contractId);
    }


    @PostMapping(value = "/basket")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void submitBasket(@RequestParam("contractId") Integer contractId) throws OperationFailed {

        contractService.submitBasket(contractId);
    }


    @PatchMapping(value = "/tariff")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void changeTariffAction(@RequestParam("contractId") Integer contractId,
                                   @RequestParam("tariffId") Integer tariffId) throws OperationFailed {

        contractService.changeContractTariff(contractId, tariffId);
    }


    @PatchMapping(value = "/block")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void blockContract(@RequestParam("contractId") Integer contractId,
                              Authentication auth) throws OperationFailed {

        UserDetailsServiceImpl.ExtendUser user = (UserDetailsServiceImpl.ExtendUser)auth.getPrincipal();
        contractService.blockContract(user.getId(), contractId);
    }

    @PatchMapping(value = "/unblock")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public void unblockContract(@RequestParam("contractId") Integer contractId,
                                Authentication auth) throws OperationFailed {

        UserDetailsServiceImpl.ExtendUser user = (UserDetailsServiceImpl.ExtendUser)auth.getPrincipal();
        contractService.unblockContract(user.getId(), contractId);
    }

}
