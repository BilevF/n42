package com.bilev.controller;

import com.bilev.dto.*;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.exception.UnableToUpdateException;
import com.bilev.model.Role;
import com.bilev.service.api.ContractService;
import com.bilev.service.api.TariffService;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
public class ContractController {

    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @Autowired
    private TariffService tariffService;

    @RequestMapping(value = "/newContract", method = RequestMethod.GET)
    public String newContract(ModelMap model, @RequestParam("userId") Integer userId) {
        BasicContractDto contractDto = new BasicContractDto();
        contractDto.setUserId(userId);

        model.addAttribute("contract", contractDto);
        model.addAttribute("tariffs", tariffService.getAvailableTariffs());
        return "editContract";
    }


    @RequestMapping(value = "/addContract", method = RequestMethod.POST)
    public String addContract(@Valid @ModelAttribute("contract") BasicContractDto contract,
                              BindingResult bindingResult,
                              ModelMap model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tariffs", tariffService.getAvailableTariffs());
            return "editContract";
        }

        try {
            userService.saveContract(contract);
            redirectAttributes.addAttribute("userId", contract.getUserId());
            return "redirect:/user";
        } catch (UnableToSaveException e) {
            model.addAttribute("exception", e.getMessage());
            model.addAttribute("tariffs", tariffService.getAvailableTariffs());
            return "editContract";
        }

    }

    @RequestMapping(value = "/contract", method = RequestMethod.GET)
    public String contract(ModelMap model, @RequestParam("contractId") Integer contractId,
                           Principal principal,
                           RedirectAttributes redirectAttributes) {
        try {
            UserDto user = userService.getUserByEmail(principal.getName());
            ContractDto contractDto = contractService.getContract(contractId);

            if (user.getRoleRoleName() == Role.RoleName.ROLE_CLIENT && !user.getId().equals(contractDto.getUserId()))
                return "redirect:/account";

            Collection<BasicOptionDto> optionsDto = contractService.getAvailableOptionsForContract(contractId);
            model.addAttribute("availableOptions", optionsDto);
            model.addAttribute("contract", contractDto);
            return "contract";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }

    }

    @RequestMapping(value = "/addNewOption", method = RequestMethod.GET)
    public String addNewOption(ModelMap model, @RequestParam("contractId") Integer contractId,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        try {
            UserDto user = userService.getUserByEmail(principal.getName());//TODO not found
            ContractDto contractDto = contractService.getContract(contractId);

            if (user.getRoleRoleName() == Role.RoleName.ROLE_CLIENT && !user.getId().equals(contractDto.getUserId()))
                return "redirect:/account";


            Collection<BasicOptionDto> optionsDto = contractService.getAvailableOptionsForContract(contractId);
            model.addAttribute("availableOptions", optionsDto);
            model.addAttribute("contract", contractDto);
            return "optionsBasket";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }
    }

    @RequestMapping(value = "/addToBasket", method = RequestMethod.POST)
    public String addToBasket(@RequestParam("contractId") Integer contractId,
                              @RequestParam("optionId") Integer optionId,
                              RedirectAttributes redirectAttributes) {
        try {
            contractService.addOptionToBasket(contractId, optionId);
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("contractId", contractId);
        return "redirect:/addNewOption";
    }

    @RequestMapping(value = "/removeFromBasket", method = RequestMethod.POST)
    public String removeFromBasket(@RequestParam("contractId") Integer contractId,
                                   @RequestParam("optionId") Integer optionId,
                                   RedirectAttributes redirectAttributes) {

        try {
            contractService.removeOptionFromBasket(contractId, optionId);
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("contractId", contractId);
        return "redirect:/addNewOption";
    }

    @RequestMapping(value = "/clearBasket", method = RequestMethod.POST)
    public String clearBasket(@RequestParam("contractId") Integer contractId,
                              RedirectAttributes redirectAttributes) {

        try {
            contractService.clearBasket(contractId);
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("contractId", contractId);
        return "redirect:/addNewOption";

    }

    @RequestMapping(value = "/submitBasket", method = RequestMethod.POST)
    public String submitBasket(@RequestParam("contractId") Integer contractId,
                               RedirectAttributes redirectAttributes) {

        try {
            contractService.submitBasket(contractId);
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        } catch (NotFoundException | UnableToUpdateException | UnableToSaveException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/addNewOption";
        }
    }

    @RequestMapping(value = "/selectTariff", method = RequestMethod.GET)
    public String selectTariff(ModelMap model,
                               @RequestParam("contractId") Integer contractId,
                               RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("tariffs", tariffService.getAvailableTariffs(contractId));
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        }
        model.addAttribute("title", "Select tariff");
        model.addAttribute("path", "/changeTariff");
        model.addAttribute("method", "post");
        model.addAttribute("hiddenName", "contractId");
        model.addAttribute("hiddenValue", contractId);
        model.addAttribute("btnName", "Select");
        return "tariffs";
    }

    @RequestMapping(value = "/changeTariff", method = RequestMethod.POST)
    public String changeTariff(@RequestParam("contractId") Integer contractId,
                               @RequestParam("tariffId") Integer tariffId,
                               RedirectAttributes redirectAttributes) {
        try {
            contractService.changeContractTariff(contractId, tariffId);
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/selectTariff";
        }

    }

    @RequestMapping(value = "/changeContractStatus", method = RequestMethod.POST)
    public String changeContractStatus(@RequestParam("contractId") Integer contractId,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) {

        try {
            UserDto user = userService.getUserByEmail(principal.getName());
            contractService.changeBlockStatus(user.getId(), contractId);
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        }

    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String history(@RequestParam("contractId") Integer contractId,
                          Principal principal,
                          ModelMap model,
                          RedirectAttributes redirectAttributes) {
        try {
            Collection<HistoryDto> history = contractService.getContractHistory(contractId);
            model.addAttribute("history", history);
            return "history";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }
    }
}
