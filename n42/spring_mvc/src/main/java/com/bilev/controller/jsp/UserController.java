package com.bilev.controller.jsp;

import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
        return new ModelAndView("editUser", "user", new BasicUserDto());
    }


    @GetMapping
    public String userPage(@RequestParam("userId") Integer userId, ModelMap model) throws OperationFailed {

        UserDto user = userService.getClientById(userId);

        model.addAttribute("client", user);

        return "clientAccount";
    }

    @GetMapping(value = "/list")
    public String userListPage(ModelMap model) throws OperationFailed {
        List<BasicUserDto> users = userService.getAllClients();
        model.addAttribute("users", users);
        return "users";
    }


}
