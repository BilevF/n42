package com.bilev.controller.jsp;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;

import com.bilev.dto.HistoryDto;

import com.bilev.exception.service.OperationFailed;

import com.bilev.service.api.ContractService;
import com.bilev.service.api.SecurityService;
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

import java.util.Collection;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;

    private final TariffService tariffService;

    private final SecurityService securityService;

    @Autowired
    public ContractController(ContractService contractService, TariffService tariffService, SecurityService securityService) {
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.securityService = securityService;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(final Exception e) {

        ModelAndView model = new ModelAndView("serverError");
        model.addObject("message", "Something bad happened");

        return model;
    }

    @ExceptionHandler(OperationFailed.class)
    public ModelAndView operationFailedHandler(final OperationFailed e) {

        ModelAndView model = new ModelAndView("serverError");
        model.addObject("message", e.getMessage());

        return model;
    }

//    private boolean hasAccess(Principal principal, int contractId) throws OperationFailed {
//
//        UserDto user = userService.getUserByEmail(principal.getName());
//        ContractDto contractDto = contractService.getContract(contractId);
//        return  user.getRoleRoleName() == Role.RoleName.ROLE_ADMIN || user.getId().equals(contractDto.getUserId());
//    }
//
//    private boolean hasAccess(Principal principal, ContractDto contractDto) throws OperationFailed {
//
//        UserDto user = userService.getUserByEmail(principal.getName());
//        return  user.getRoleRoleName() == Role.RoleName.ROLE_ADMIN || user.getId().equals(contractDto.getUserId());
//    }



    @GetMapping(value = "/new")
    public String newContractPage(ModelMap model, @RequestParam("userId") Integer userId) throws OperationFailed {
        BasicContractDto contractDto = new BasicContractDto();
        contractDto.setUserId(userId);

        model.addAttribute("contract", contractDto);
        model.addAttribute("tariffs", tariffService.getAvailableTariffs());

        return "editContract";
    }


    @GetMapping
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public String contractPage(@RequestParam("contractId") Integer contractId, ModelMap model) throws OperationFailed {

        ContractDto contractDto = contractService.getContract(contractId);

        model.addAttribute("contract", contractDto);

        return "contract";
    }


    @GetMapping(value = "/basket")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public String addNewOptionPage(@RequestParam("contractId") Integer contractId, ModelMap model) throws OperationFailed {

        ContractDto contractDto = contractService.getContract(contractId);

        Collection<BasicOptionDto> optionsDto = contractService.getAvailableOptionsForContract(contractId);

        model.addAttribute("availableOptions", optionsDto);

        model.addAttribute("contract", contractDto);

        return "optionsBasket";
    }


    @GetMapping(value = "/tariff/change")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public String changeTariffPage(@RequestParam("contractId") Integer contractId, ModelMap model) throws OperationFailed {

        model.addAttribute("tariffs", tariffService.getAvailableTariffs(contractId));
        model.addAttribute("title", "Select tariff");
        model.addAttribute("path", "/changeTariff");
        model.addAttribute("method", "post");
        model.addAttribute("hiddenName", "contractId");
        model.addAttribute("hiddenValue", contractId);
        model.addAttribute("btnName", "Select");
        model.addAttribute("showBtn", true);

        return "tariffs";
    }


    @GetMapping(value = "/history")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public String historyPage(@RequestParam("contractId") Integer contractId, ModelMap model) throws OperationFailed {

        Collection<HistoryDto> history = contractService.getContractHistory(contractId);

        model.addAttribute("history", history);
        model.addAttribute("contractId", contractId);

        return "history";
    }

    @GetMapping(value = "/money")
    @PreAuthorize("@securityService.hasAccess(principal, #contractId)")
    public String moneyPage(@RequestParam("contractId") Integer contractId, ModelMap model) throws OperationFailed {

        ContractDto contract = contractService.getContract(contractId);

        model.addAttribute("contract", contract);

        return "addMoney";
    }

}
