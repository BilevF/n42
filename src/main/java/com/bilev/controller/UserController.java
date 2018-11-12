package com.bilev.controller;

import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToSaveException;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ExceptionHandler;
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
    private UserService userService;

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception e) {

        return "serverError";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public ModelAndView newUser() {
        return new ModelAndView("editUser", "user", new BasicUserDto());
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserAction(ModelMap model, @Valid @ModelAttribute("user") BasicUserDto user,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) return "editUser";

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
    public String findUser(@RequestParam("phone") String phoneOrEmail,
                           RedirectAttributes redirectAttributes) {
        int userId;
        try {
            userId = userService.findUserByPhone(phoneOrEmail);
        } catch (NotFoundException e) {
            try {
                userId = userService.getClientByEmail(phoneOrEmail).getId();
            } catch (NotFoundException e1) {
                redirectAttributes.addFlashAttribute("exception", e.getMessage());
                return "redirect:/account";
            }
        }
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/user";
    }


}
