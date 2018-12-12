package com.bilev.controller.jsp;

import com.bilev.dto.UserDto;
import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.UserService;
import com.bilev.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String accountPage(ModelMap model, Authentication auth) {

        UserDetailsServiceImpl.ExtendUser currentUser = (UserDetailsServiceImpl.ExtendUser)auth.getPrincipal();
        UserDto user;
        try {
            user = userService.getUserById(currentUser.getId());
        } catch (OperationFailed e) {

//            model.addAttribute("exception", e.getMessage());
            return "redirect:/logout";
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

    @GetMapping(value = "/newPassword")
    public String newPasswordPage(@RequestParam("userId") Integer userId,
                                  @RequestParam("token") String token,
                                  ModelMap model) {

        model.addAttribute("token", token);
        model.addAttribute("userId", userId);

        return "newPassword";
    }

    @GetMapping(value = "/forgotPassword")
    public String newPasswordPage() {
        return "forgotPassword";
    }

}
