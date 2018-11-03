package com.bilev.controller;

import com.bilev.model.User;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

        User user = userService.findUser(principal.getName());
        switch (user.getRole().getRoleName()) {
            case ROLE_CLIENT:
                model.addAttribute("client", user);
                return "clientAccount";
            case ROLE_ADMIN:
                model.addAttribute("admin", user);
                return "adminAccount";
        }

        model.addAttribute("msg", "Login error");
        return "redirect:/";
    }

}
