package com.bilev.service.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.ContractDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.Contract;
import com.bilev.model.Role;
import com.bilev.model.User;
import com.bilev.service.api.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private final ContractDao contractDao;

    private final UserDao userDao;

    @Autowired
    public SecurityServiceImpl(ContractDao contractDao, UserDao userDao) {
        this.contractDao = contractDao;
        this.userDao = userDao;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean hasAccess(Principal principal, int contractId) {

        try {
            User user = userDao.findByEmail(principal.getName());

            Contract contract = contractDao.getByKey(contractId);

            return  user.getRole().getRoleName() == Role.RoleName.ROLE_ADMIN ||
                    user.getId().equals(contract.getId());

        } catch (UnableToFindException e) {
            return false;
        }
    }
}
