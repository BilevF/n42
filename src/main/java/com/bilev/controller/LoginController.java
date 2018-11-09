package com.bilev.controller;

import com.bilev.dto.UserDto;
import com.bilev.exception.NotFoundException;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
@SessionAttributes("roles")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String validateUsr() {
        return "redirect:/account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String validateUsr(ModelMap model, Principal principal) {

        UserDto user = null;
        try {
            user = userService.getUserByEmail(principal.getName());
        } catch (NotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "redirect:/";
        }
        switch (user.getRoleRoleName()) {
            case ROLE_CLIENT:
                model.addAttribute("client", user);
                return "clientAccount";
            case ROLE_ADMIN:
                model.addAttribute("admin", user);
                return "adminAccount";
        }
        return "redirect:/";
    }

}
