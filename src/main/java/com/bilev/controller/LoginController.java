package com.bilev.controller;

import com.bilev.model.Admin;
import com.bilev.model.Client;
import com.bilev.model.User;
import com.bilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

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
        if (user instanceof Client) {
            model.addAttribute("client", user);
            return "clientAccount";
        } else if (user instanceof Admin) {
            model.addAttribute("admin", user);
            return "adminAccount";
        }

        model.addAttribute("msg", "Login error");
        return "redirect:/";
    }

}
