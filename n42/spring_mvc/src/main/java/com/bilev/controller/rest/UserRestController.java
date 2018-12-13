package com.bilev.controller.rest;


import com.bilev.dto.BasicUserDto;
import com.bilev.dto.MoneyTransferDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.service.OperationFailed;
import com.bilev.model.User;
import com.bilev.service.api.MailService;
import com.bilev.service.api.SecurityService;
import com.bilev.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MailService mailService;


    @PostMapping
    public Integer addUser(@RequestBody @Valid BasicUserDto user) throws OperationFailed {

        int userId = userService.saveUser(user);

        try {
            String token = securityService.getMailToken(userId);
            mailService.sendNewPwdLink(user.getEmail(), token);
        } catch (Exception ignored) { }

        return userId;
    }

    @PatchMapping
    @PreAuthorize("@securityService.hasAccessToUser(#user)")
    public void updateUser(@RequestBody @Valid BasicUserDto user)
            throws OperationFailed {

        userService.updateUser(user);
    }

    @PatchMapping(value = "/password")
    @PreAuthorize("@securityService.isValidMailToken(#userId, #token)")
    public void updatePassword(@RequestParam("userId") Integer userId,
                               @RequestParam("token") String token,
                               @RequestParam("password") String password)
            throws OperationFailed {

        userService.updateUserPassword(userId, password);
    }

    @PostMapping(value = "/password/link")
    public void sendPassword(@RequestParam("email") String email)
            throws OperationFailed {


        UserDto user = userService.getClientByEmail(email);
        try {
            String token = securityService.getMailToken(user.getId());
            mailService.sendNewPwdLink(user.getEmail(), token);
        } catch (Exception ignored) { }
    }



    @GetMapping(value = "/find")
    public Integer findUser(@RequestParam("phoneOrEmail") String phoneOrEmail) throws OperationFailed {

        try {
            return userService.getUserByPhone(phoneOrEmail).getId();
        } catch (OperationFailed operationFailed) {
            return userService.getClientByEmail(phoneOrEmail).getId();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/money", consumes = {"application/x-www-form-urlencoded"})
    @PreAuthorize("@securityService.isTrustedTransaction(#transferDto)")
    public void addMoney(MoneyTransferDto transferDto) throws Exception {

        if (!transferDto.getLabel().isEmpty() && transferDto.getWithdraw_amount() != null) {
            int userId = Integer.parseInt(transferDto.getLabel());
            double amount = Double.parseDouble(transferDto.getWithdraw_amount());

            userService.addMoney(userId, amount);
        }
    }
}
