package com.bilev.controller;

import com.bilev.dto.*;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.service.api.ContractService;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public ModelAndView newUser() {
        return new ModelAndView("editUser", "user", new BasicUserDto());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(ModelMap model, @Valid @ModelAttribute("user") BasicUserDto user,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "editUser";

        try {
            int userId = userService.saveUser(user);
            redirectAttributes.addAttribute("userId", userId);
            return "redirect:/user";
        } catch (UnableToSaveException e) {
            model.addAttribute("exception", e.getMessage());
            return "editUser";
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(ModelMap model, @RequestParam("userId") Integer userId,
                       RedirectAttributes redirectAttributes) {

        try {
            UserDto user = userService.getUserById(userId);
            model.addAttribute("client", user);
            return "clientAccount";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }

    }

    @RequestMapping(value = "/allUser", method = RequestMethod.GET)
    public String allUser(ModelMap model) {
        List<BasicUserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public String findUser(@RequestParam("phone") String phone,
                           RedirectAttributes redirectAttributes) {
        try {
            int userId = userService.findUserByPhone(phone);
            redirectAttributes.addAttribute("userId", userId);
            return "redirect:/user";

        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
            return "redirect:/account";
        }
    }


}
