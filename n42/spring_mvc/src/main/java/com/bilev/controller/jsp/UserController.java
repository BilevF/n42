package com.bilev.controller.jsp;

import com.bilev.dto.BasicUserDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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


    @GetMapping(value = "/new")
    public ModelAndView newUserPage() {
        return new ModelAndView("createUser", "user", new BasicUserDto());
    }

    @GetMapping(value = "/update")
    @PreAuthorize("@securityService.hasAccessToUser(#userId)")
    public ModelAndView updateUserPage(@RequestParam("userId") Integer userId, Principal principal)
            throws OperationFailed {

        BasicUserDto user = userService.getUserById(userId);

        return new ModelAndView("updateUser", "user", user);
    }

    @GetMapping
    public String userPage(@RequestParam("userId") Integer userId, ModelMap model) throws OperationFailed {

        UserDto user = userService.getClientById(userId);

        model.addAttribute("client", user);

        return "clientAccount";
    }

    @GetMapping(value = "/list")
    public String userListPage(ModelMap model) throws OperationFailed {
        List<UserDto> users = userService.getAllClients();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/money")
    @PreAuthorize("@securityService.hasAccessToUser(#userId)")
    public String moneyPage(@RequestParam("userId") Integer userId, ModelMap model)
            throws OperationFailed {

        UserDto user = userService.getClientById(userId);

        model.addAttribute("user", user);

        return "addMoney";
    }


}
