package com.bilev.service.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.MoneyTransferDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
import com.bilev.model.Contract;
import com.bilev.model.Role;
import com.bilev.model.User;
import com.bilev.service.api.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService, ServiceErrors {

    private final String notification_secret = "SfqYHJ1s7uIPwpJTzG+rG3wj";

    private final String mail_secret = "Wk9_To1s+w24DOTzd2-rV2rn";

    private final ContractDao contractDao;

    private final UserDao userDao;

    private final ShaPasswordEncoder shaEncoder;

    @Autowired
    public SecurityServiceImpl(ContractDao contractDao, UserDao userDao, ShaPasswordEncoder shaEncoder) {
        this.contractDao = contractDao;
        this.userDao = userDao;
        this.shaEncoder = shaEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean hasAccessToContract(int contractId) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsServiceImpl.ExtendUser currentUser = (UserDetailsServiceImpl.ExtendUser)auth.getPrincipal();

            User user = userDao.getByKey(currentUser.getId());
            if (user == null) return false;

            Contract contract = contractDao.getByKey(contractId);
            if (contract == null) return false;

            return  user.getRole().getRoleName() == Role.RoleName.ROLE_ADMIN ||
                    user.getId().equals(contract.getUser().getId());

        } catch (UnableToFindException e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasAccessToContract(BasicContractDto contractDto) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsServiceImpl.ExtendUser currentUser = (UserDetailsServiceImpl.ExtendUser)auth.getPrincipal();

            User user = userDao.getByKey(currentUser.getId());
            if (user == null) return false;

            Contract contract = contractDao.getByKey(contractDto.getId());

            return  user.getRole().getRoleName() == Role.RoleName.ROLE_ADMIN ||
                    user.getId().equals(contract.getUser().getId());

        } catch (UnableToFindException e) {
            return false;
        }
    }


    @Override
    public boolean hasAccessToUser(int userId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsServiceImpl.ExtendUser user = (UserDetailsServiceImpl.ExtendUser)auth.getPrincipal();;

        return  user.getId().equals(userId);
    }

    @Override
    public boolean hasAccessToUser(BasicUserDto userDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsServiceImpl.ExtendUser user = (UserDetailsServiceImpl.ExtendUser)auth.getPrincipal();

        return  user.getId().equals(userDto.getId());
    }

    //notification_type&operation_id&amount&currency&datetime&sender&codepro&notification_secret&label

    @Override
    public boolean isTrustedTransaction(MoneyTransferDto transferDto) {

        String str = transferDto.getNotification_type() +
                "&" +
                transferDto.getOperation_id() +
                "&" +
                transferDto.getAmount() +
                "&" +
                transferDto.getCurrency() +
                "&" +
                transferDto.getDatetime() +
                "&" +
                transferDto.getSender() +
                "&" +
                transferDto.getCodepro() +
                "&" +
                notification_secret +
                "&" +
                transferDto.getLabel();

        String token = shaEncoder.encodePassword(str, null);

        return token.equals(transferDto.getSha1_hash()) &&
                !transferDto.getCodepro();
    }

    private String getToken(User user) {
        if (user == null) return "";
        return user.getFirstName() + mail_secret + user.getPassport() + user.getPassword();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValidMailToken(int userId, String token) {
        try {

            User user = userDao.getClientById(userId);
            if (user == null || token == null) return false;

            return shaEncoder.encodePassword(getToken(user), null).equals(token);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getMailToken(int userId) throws OperationFailed {
        try {

            User user = userDao.getClientById(userId);
            if (user == null) throw new OperationFailed(USER_NOT_FOUND);

            return shaEncoder.encodePassword(getToken(user), null);

        } catch (Exception e) {
            throw new OperationFailed(UNABLE_TO_SUBMIT);
        }
    }
}
