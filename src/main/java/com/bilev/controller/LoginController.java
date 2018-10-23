package com.bilev.controller;

import com.bilev.model.User;
import com.bilev.service.AuthService;
import com.bilev.service.ContractService;
import com.bilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView validateUsr(@RequestParam("username")String username, @RequestParam("password")String password) {
        if (username != null && password != null && authService.findUser(username, password)) {
            User user = userService.findUser(username, password);
            ModelAndView modelAndView = new ModelAndView("account");
            modelAndView.addObject("user", user);
            modelAndView.addObject("contracts", contractService.getUserContracts(user.getId()));
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/", "msg", "Login error");
        }
    }
}
