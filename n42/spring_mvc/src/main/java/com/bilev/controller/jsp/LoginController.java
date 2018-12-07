package com.bilev.controller.jsp;

import com.bilev.dto.UserDto;
import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
@SessionAttributes("roles")
public class LoginController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception e) {

        return "forward:/serverError";
    }

    @PostMapping(value = "/account")
    public String validateUsr() {
        return "redirect:/account";
    }

    @GetMapping(value = "/account")
    public String accountPage(ModelMap model, Principal principal) {

        UserDto user;
        try {
            user = userService.getUserByEmail(principal.getName());
        } catch (OperationFailed e) {
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
