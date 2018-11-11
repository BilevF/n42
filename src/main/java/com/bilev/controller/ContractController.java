package com.bilev.controller;

import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.UserDto;
import com.bilev.dto.HistoryDto;
import com.bilev.exception.AccessException;
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

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
public class ContractController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private TariffService tariffService;

    @ExceptionHandler(AccessException.class)
    public String handleAllException(AccessException e) {
        //redirectAttributes.addFlashAttribute("exception", e.getMessage());
        return "redirect:/account";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception e) {

        return "forward:/serverError";
    }

    private boolean hasAccess(Principal principal, int contractId) throws NotFoundException {
        try {
            UserDto user = userService.getUserByEmail(principal.getName());
            ContractDto contractDto = contractService.getContract(contractId);
            return  user.getRoleRoleName() == Role.RoleName.ROLE_ADMIN || user.getId().equals(contractDto.getUserId());
        } catch (NotFoundException e) {
            throw new NotFoundException("Contract not found");
        }
    }

    private boolean hasAccess(Principal principal, ContractDto contractDto) throws NotFoundException {
        try {
            UserDto user = userService.getUserByEmail(principal.getName());
            return  user.getRoleRoleName() == Role.RoleName.ROLE_ADMIN || user.getId().equals(contractDto.getUserId());
        } catch (NotFoundException e) {
            throw new NotFoundException("User not found");
        }
    }

    @RequestMapping(value = "/newContract", method = RequestMethod.GET)
    public String newContract(ModelMap model, @RequestParam("userId") Integer userId) {
        BasicContractDto contractDto = new BasicContractDto();
        contractDto.setUserId(userId);

        model.addAttribute("contract", contractDto);
        model.addAttribute("tariffs", tariffService.getAvailableTariffs());
        return "editContract";
    }


    @RequestMapping(value = "/newContract", method = RequestMethod.POST)
    public String newContractAction(@Valid @ModelAttribute("contract") BasicContractDto contract,
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
        } catch (UnableToSaveException | NotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            model.addAttribute("tariffs", tariffService.getAvailableTariffs());
            return "editContract";
        }

    }

    @RequestMapping(value = "/contract", method = RequestMethod.GET)
    public String contract(ModelMap model, @RequestParam("contractId") Integer contractId,
                           Principal principal,
                           RedirectAttributes redirectAttributes) throws AccessException {
        try {
            ContractDto contractDto = contractService.getContract(contractId);

            if (!hasAccess(principal, contractDto)) throw new AccessException("Access denied");

            Collection<BasicOptionDto> optionsDto = contractService.getAvailableOptionsForContract(contractId);
            model.addAttribute("availableOptions", optionsDto);
            model.addAttribute("contract", contractDto);
            return "contract";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }

    }


    @RequestMapping(value = "/removeContractOption", method = RequestMethod.POST)
    public String removeContractOption(ModelMap model, @RequestParam("contractId") Integer contractId,
                                       @RequestParam("optionId") Integer optionId,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) throws AccessException {
        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");

            contractService.removeOptionFromContract(contractId, optionId);
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("contractId", contractId);
        return "redirect:/contract";
    }

    @RequestMapping(value = "/addNewOption", method = RequestMethod.GET)
    public String addNewOption(ModelMap model, @RequestParam("contractId") Integer contractId,
                               Principal principal,
                               RedirectAttributes redirectAttributes) throws AccessException {

        try {
            ContractDto contractDto = contractService.getContract(contractId);
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");

            Collection<BasicOptionDto> optionsDto = contractService.getAvailableOptionsForContract(contractId);
            model.addAttribute("availableOptions", optionsDto);
            model.addAttribute("contract", contractDto);
            return "optionsBasket";
        } catch (NotFoundException  e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        }
    }

    @RequestMapping(value = "/addToBasket", method = RequestMethod.POST)
    public String addToBasket(@RequestParam("contractId") Integer contractId,
                              @RequestParam("optionId") Integer optionId,
                              RedirectAttributes redirectAttributes,
                              Principal principal) throws AccessException {
        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
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
                                   RedirectAttributes redirectAttributes,
                                   Principal principal) throws AccessException {

        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
            contractService.removeOptionFromBasket(contractId, optionId);
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("contractId", contractId);
        return "redirect:/addNewOption";
    }

    @RequestMapping(value = "/clearBasket", method = RequestMethod.POST)
    public String clearBasket(@RequestParam("contractId") Integer contractId,
                              RedirectAttributes redirectAttributes,
                              Principal principal) throws AccessException {

        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
            contractService.clearBasket(contractId);
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        redirectAttributes.addAttribute("contractId", contractId);
        return "redirect:/addNewOption";

    }

    @RequestMapping(value = "/submitBasket", method = RequestMethod.POST)
    public String submitBasket(@RequestParam("contractId") Integer contractId,
                               RedirectAttributes redirectAttributes,
                               Principal principal) throws AccessException {

        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
            contractService.submitBasket(contractId);
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        } catch (NotFoundException | UnableToUpdateException | UnableToSaveException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/addNewOption";
        }
    }

    @RequestMapping(value = "/changeTariff", method = RequestMethod.GET)
    public String changeTariff(ModelMap model,
                               @RequestParam("contractId") Integer contractId,
                               RedirectAttributes redirectAttributes,
                               Principal principal) throws AccessException {
        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
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
    public String changeTariffAction(@RequestParam("contractId") Integer contractId,
                               @RequestParam("tariffId") Integer tariffId,
                               RedirectAttributes redirectAttributes,
                               Principal principal) throws AccessException {
        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
            contractService.changeContractTariff(contractId, tariffId);
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        } catch (NotFoundException | UnableToUpdateException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/changeTariff";
        }

    }

    @RequestMapping(value = "/changeContractStatus", method = RequestMethod.POST)
    public String changeContractStatus(@RequestParam("contractId") Integer contractId,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) throws AccessException {

        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
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
                          RedirectAttributes redirectAttributes) throws AccessException {
        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
            Collection<HistoryDto> history = contractService.getContractHistory(contractId);
            model.addAttribute("history", history);
            return "history";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }
    }

    @RequestMapping(value = "/addMoney", method = RequestMethod.GET)
    public String addMoney(@RequestParam("contractId") Integer contractId,
                          Principal principal,
                          ModelMap model,
                          RedirectAttributes redirectAttributes) throws AccessException {

        try {
            ContractDto contract = contractService.getContract(contractId);

            if (!hasAccess(principal, contract)) throw new AccessException("Access denied");

            model.addAttribute("contract", contract);
            return "addMoney";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }
    }

    @RequestMapping(value = "/addMoney", method = RequestMethod.POST)
    public String addMoneyAction(@RequestParam("contractId") Integer contractId,
                                 @RequestParam("moneyValue") Integer moneyValue,
                           Principal principal,
                           ModelMap model,
                           RedirectAttributes redirectAttributes) throws AccessException {

        try {
            if (!hasAccess(principal, contractId)) throw new AccessException("Access denied");
            contractService.addMoney(contractId, moneyValue);
            redirectAttributes.addAttribute("contractId", contractId);
            return "redirect:/contract";
        } catch (NotFoundException | UnableToUpdateException | UnableToSaveException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "addMoney";
        }
    }
}
