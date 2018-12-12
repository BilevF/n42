package com.bilev.controller.jsp;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;

import com.bilev.dto.HistoryDto;

import com.bilev.exception.service.OperationFailed;

import com.bilev.model.Contract;
import com.bilev.service.api.ContractService;
import com.bilev.service.api.TariffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;

    private final TariffService tariffService;


    @Autowired
    public ContractController(ContractService contractService, TariffService tariffService) {
        this.contractService = contractService;
        this.tariffService = tariffService;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(final Exception e) {

        ModelAndView model = new ModelAndView("serverError");
        model.addObject("message", "We'll be back!");

        return model;
    }

    @ExceptionHandler(OperationFailed.class)
    public ModelAndView operationFailedHandler(final OperationFailed e) {

        ModelAndView model = new ModelAndView("serverError");
        model.addObject("message", e.getMessage());

        return model;
    }





    @GetMapping(value = "/new")
    public String newContractPage(ModelMap model, @RequestParam("userId") Integer userId) throws OperationFailed {
        BasicContractDto contractDto = new BasicContractDto();
        contractDto.setUserId(userId);

        model.addAttribute("contract", contractDto);
        model.addAttribute("tariffs", tariffService.getAvailableTariffs());

        return "createContract";
    }

    @GetMapping(value = "/update")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public String updateContractPage(ModelMap model, @RequestParam("contractId") Integer contractId) throws OperationFailed {
        BasicContractDto contract = contractService.getContract(contractId);

        model.addAttribute("contract", contract);

        return "updateContract";
    }


    @GetMapping
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public String contractPage(@RequestParam("contractId") Integer contractId, ModelMap model)
            throws OperationFailed {

        ContractDto contractDto = contractService.getContract(contractId);

        model.addAttribute("contract", contractDto);

        return "contract";
    }


    @GetMapping(value = "/basket")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public String addNewOptionPage(@RequestParam("contractId") Integer contractId, ModelMap model)
            throws OperationFailed {

        ContractDto contractDto = contractService.getContract(contractId);

        Collection<BasicOptionDto> optionsDto = contractService.getAvailableOptionsForContract(contractId);

        model.addAttribute("availableOptions", optionsDto);

        model.addAttribute("contract", contractDto);

        return "optionsBasket";
    }


    @GetMapping(value = "/tariff/change")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public String changeTariffPage(@RequestParam("contractId") Integer contractId, ModelMap model)
            throws OperationFailed {
        ContractDto contract = contractService.getContract(contractId);

        model.addAttribute("tariffs", tariffService.getAvailableTariffs(contractId));
        model.addAttribute("contract", contract);

        return "selectTariff";
    }


    @GetMapping(value = "/history")
    @PreAuthorize("@securityService.hasAccessToContract(#contractId)")
    public String historyPage(@RequestParam("contractId") Integer contractId, ModelMap model)
            throws OperationFailed {

        ContractDto contract = contractService.getContract(contractId);

        model.addAttribute("contract", contract);

        return "history";
    }

}
