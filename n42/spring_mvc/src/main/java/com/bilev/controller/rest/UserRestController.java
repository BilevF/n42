package com.bilev.controller.rest;


import com.bilev.dto.BasicUserDto;
import com.bilev.exception.service.OperationFailed;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping
    public Integer addUser(@RequestBody @Valid BasicUserDto user) throws OperationFailed {

        return userService.saveUser(user);
    }

    @GetMapping(value = "/find")
    public Integer findUser(@RequestParam("phoneOrEmail") String phoneOrEmail) throws OperationFailed {

        try {
            return userService.getUserByPhone(phoneOrEmail).getId();
        } catch (OperationFailed operationFailed) {
            return userService.getClientByEmail(phoneOrEmail).getId();
        }
    }
}
