package com.bilev.controller;

import com.bilev.dto.*;
import com.bilev.model.Role;
import com.bilev.service.api.ContractService;
import com.bilev.service.api.TariffService;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collection;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @Autowired
    private TariffService tariffService;

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public ModelAndView newUser() {
        return new ModelAndView("editUser", "user", new BasicUserDto());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") BasicUserDto user, RedirectAttributes redirectAttributes) {
        int userId = userService.saveUser(user);
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(ModelMap model, @RequestParam("userId") Integer userId) {
        UserDto user = userService.getUserById(userId);
        model.addAttribute("client", user);
        return "clientAccount";
    }

    @RequestMapping(value = "/allUser", method = RequestMethod.GET)
    public String allUser(ModelMap model) {
        List<BasicUserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @RequestMapping(value = "/newContract", method = RequestMethod.GET)
    public String newContract(ModelMap model, @RequestParam("userId") Integer userId) {
        BasicContractDto contractDto = new BasicContractDto();
        contractDto.setUserId(userId);

        model.addAttribute("contract", contractDto);
        model.addAttribute("tariffs", tariffService.getAllTariffs());
        return "editContract";
    }


    @RequestMapping(value = "/addContract", method = RequestMethod.POST)
    public String addContract(@ModelAttribute("contract") BasicContractDto contractDto, RedirectAttributes redirectAttributes) {
        int contractId = userService.saveContract(contractDto);
        redirectAttributes.addAttribute("userId", contractDto.getUserId());
        return "redirect:/user";
    }

    @RequestMapping(value = "/contract", method = RequestMethod.GET)
    public String contract(ModelMap model, @RequestParam("contractId") Integer contractId, Principal principal) {
        UserDto user = userService.getUserByEmail(principal.getName());
        ContractDto contractDto = contractService.getContract(contractId);

        if (user.getRoleRoleName() == Role.RoleName.ROLE_CLIENT && !user.getId().equals(contractDto.getUserId()))
            return "redirect:/account";
        Collection<BasicOptionDto> optionsDto = contractService.getAvailableOptionsForContract(contractId);
        model.addAttribute("availableOptions", optionsDto);
        model.addAttribute("contract", contractDto);
        return "contract";
    }

}
