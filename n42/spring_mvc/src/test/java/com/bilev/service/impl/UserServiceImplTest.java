package com.bilev.service.impl;


import com.bilev.configuration.ServiceTestConfig;
import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.RoleDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicUserDto;
import com.bilev.dto.UserDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
import com.bilev.model.Contract;
import com.bilev.model.Role;
import com.bilev.model.User;
import com.bilev.service.api.UserService;
import com.bilev.tools.ContractCreator;
import com.bilev.tools.UserCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class UserServiceImplTest implements ServiceErrors {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ContractDao contractDao;



    @Autowired
    UserCreator userCreator;

    @Autowired
    ContractCreator contractCreator;


    @Autowired
    private UserService userService;

    @Before
    public void setUp() {

    }

    private void reset() {
        Mockito.reset(roleDao);
        Mockito.reset(userDao);
        Mockito.reset(contractDao);
    }


    @Test
    public void testSaveUser() throws OperationFailed, UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicUserDto user = userCreator.getBasicDto(0);

        Role.RoleName roleName = Role.RoleName.ROLE_CLIENT;
        Role role = userCreator.getRole(roleName);
        when(roleDao.getRoleByName(roleName)).thenReturn(role);
        when(userDao.findByEmail(user.getEmail())).thenReturn(null);

        // do
        Integer id = userService.saveUser(user);

        // verify
        Assert.assertEquals(user.getId(), id);
        verify(userDao, Mockito.times(1)).persist(Mockito.any());
    }

    @Test
    public void testSaveBadUser() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicUserDto user = userCreator.getBasicDto(0);

        Role.RoleName roleName = Role.RoleName.ROLE_CLIENT;
        Role role = userCreator.getRole(roleName);
        when(roleDao.getRoleByName(roleName)).thenReturn(role);
        when(userDao.findByEmail(user.getEmail())).thenReturn(null);

        user.setAddress(null);

        try {
            // do
            userService.saveUser(user);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), VALIDATION);
            verify(userDao, Mockito.times(0)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveNotUniqueUser() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicUserDto user = userCreator.getBasicDto(0);

        Role.RoleName roleName = Role.RoleName.ROLE_CLIENT;
        Role role = userCreator.getRole(roleName);
        when(roleDao.getRoleByName(roleName)).thenReturn(role);
        when(userDao.findByEmail(user.getEmail())).thenReturn(userCreator.getEntity(0));

        try {
            // do
            userService.saveUser(user);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), EMAIL_NOT_UNIQUE);
            verify(userDao, Mockito.times(0)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveUserNoRole() throws UnableToFindException, UnableToSaveException {
        // prepare
        reset();
        BasicUserDto user = userCreator.getBasicDto(0);

        Role.RoleName roleName = Role.RoleName.ROLE_CLIENT;
        when(roleDao.getRoleByName(roleName)).thenReturn(null);
        when(userDao.findByEmail(user.getEmail())).thenReturn(null);

        try {
            // do
            userService.saveUser(user);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_SAVE);
            verify(userDao, Mockito.times(0)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveUserException() throws UnableToFindException, UnableToSaveException {
        // prepare
        reset();
        BasicUserDto user = userCreator.getBasicDto(0);

        Role.RoleName roleName = Role.RoleName.ROLE_CLIENT;
        Role role = userCreator.getRole(roleName);
        when(roleDao.getRoleByName(roleName)).thenReturn(role);
        when(userDao.findByEmail(user.getEmail())).thenReturn(null);

        doThrow(new UnableToSaveException()).when(userDao).persist(Mockito.any());

        try {
            // do
            userService.saveUser(user);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_SAVE);
            verify(userDao, Mockito.times(1)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetUserByPhone() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        User user = userCreator.getEntity(0);
        contract.setUser(user);
        when(contractDao.getContractByPhone(contract.getPhoneNumber())).thenReturn(contract);

        // do
        UserDto userDto = userService.getUserByPhone(contract.getPhoneNumber());

        // verify
        Assert.assertEquals(userDto, userCreator.getDto(0));
        verify(contractDao, Mockito.times(1)).getContractByPhone(contract.getPhoneNumber());
    }

    @Test
    public void testGetUserByNullPhone() throws UnableToFindException {
        // prepare
        reset();

        try {
            // do
            userService.getUserByPhone(null);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), USER_NOT_FOUND);
            verify(contractDao, Mockito.times(0)).getContractByPhone(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetUserByBadPhone() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);

        when(contractDao.getContractByPhone(contract.getPhoneNumber())).thenReturn(null);

        try {
            // do
            userService.getUserByPhone(contract.getPhoneNumber());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), USER_NOT_FOUND);
            verify(contractDao, Mockito.times(1)).getContractByPhone(contract.getPhoneNumber());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetUserByPhoneException() throws UnableToFindException {
        // prepare
        reset();
        String phone = "911";
        doThrow(new UnableToFindException()).when(contractDao).getContractByPhone(phone);
        try {
            // do
            userService.getUserByPhone(phone);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);
            verify(contractDao, Mockito.times(1)).getContractByPhone(phone);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetAllClients() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        List<BasicUserDto> test = userCreator.getBasicDtoList();
        when(userDao.getAllClients()).thenReturn(userCreator.getEntityList());

        // do
        List<BasicUserDto> res = userService.getAllClients();

        // verify
        Assert.assertTrue(res.containsAll(test) && test.containsAll(res));
        verify(userDao, Mockito.times(1)).getAllClients();
    }

    @Test
    public void testGetAllClientsException() throws UnableToFindException {
        // prepare
        reset();
        doThrow(new UnableToFindException()).when(userDao).getAllClients();

        try {
            // do
            userService.getAllClients();
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetClientById() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        int userId = 0;
        when(userDao.getClientById(userId)).thenReturn(userCreator.getEntity(userId));

        // do
        UserDto res = userService.getClientById(userId);

        // verify
        Assert.assertEquals(res, userCreator.getDto(userId));
        verify(userDao, Mockito.times(1)).getClientById(userId);
    }

    @Test
    public void testGetClientByBadId() throws UnableToFindException {
        // prepare
        reset();
        int userId = 0;
        when(userDao.getClientById(userId)).thenReturn(null);

        try {
            // do
            userService.getClientById(userId);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), USER_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetClientByIdException() throws UnableToFindException {
        // prepare
        reset();
        doThrow(new UnableToFindException()).when(userDao).getClientById(Mockito.any(Integer.class));

        try {
            // do
            userService.getClientById(0);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetUserById() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        int userId = 0;
        when(userDao.getByKey(userId)).thenReturn(userCreator.getEntity(userId));

        // do
        UserDto res = userService.getUserById(userId);

        // verify
        Assert.assertEquals(res, userCreator.getDto(userId));
        verify(userDao, Mockito.times(1)).getByKey(userId);
    }

    @Test
    public void testGetUserByBadId() throws UnableToFindException {
        // prepare
        reset();
        int userId = 0;
        when(userDao.getClientById(userId)).thenReturn(null);

        try {
            // do
            userService.getUserById(0);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), USER_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetUserByIdException() throws UnableToFindException {
        // prepare
        reset();
        int userId = 0;
        when(userDao.getByKey(userId)).thenReturn(userCreator.getEntity(userId));
        doThrow(new UnableToFindException()).when(userDao).getByKey(Mockito.any(Integer.class));

        try {
            // do
            userService.getUserById(0);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetUserByEmail() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        User user = userCreator.getEntity(0);
        UserDto userDto = userCreator.getDto(0);
        when(userDao.findByEmail(user.getEmail())).thenReturn(user);

        // do
        UserDto res = userService.getUserByEmail(user.getEmail());

        // verify
        Assert.assertEquals(res, userDto);
        verify(userDao, Mockito.times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void testGetUserByNullEmail() throws UnableToFindException {
        // prepare
        reset();

        try {
            // do
            userService.getUserByEmail(null);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), USER_NOT_FOUND);
            verify(userDao, Mockito.times(0)).findByEmail(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetUserByBadEmail() throws UnableToFindException {
        // prepare
        reset();
        User user = userCreator.getEntity(0);
        when(userDao.findByEmail(user.getEmail())).thenReturn(null);

        try {
            // do
            userService.getUserByEmail(user.getEmail());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), USER_NOT_FOUND);
            verify(userDao, Mockito.times(1)).findByEmail(user.getEmail());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetUserByEmailException() throws UnableToFindException {
        // prepare
        reset();
        User user = userCreator.getEntity(0);
        doThrow(new UnableToFindException()).when(userDao).findByEmail(user.getEmail());

        try {
            // do
            userService.getUserByEmail(user.getEmail());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);
            verify(userDao, Mockito.times(1)).findByEmail(user.getEmail());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetClientByEmail() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        User user = userCreator.getEntity(0);
        UserDto userDto = userCreator.getDto(0);
        when(userDao.findClientByEmail(user.getEmail())).thenReturn(user);

        // do
        UserDto res = userService.getClientByEmail(user.getEmail());

        // verify
        Assert.assertEquals(res, userDto);
        verify(userDao, Mockito.times(1)).findClientByEmail(user.getEmail());
    }

    @Test
    public void testGetClientByNullEmail() throws UnableToFindException {
        // prepare
        reset();

        try {
            // do
            userService.getClientByEmail(null);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), USER_NOT_FOUND);
            verify(userDao, Mockito.times(0)).findClientByEmail(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetClientByBadEmail() throws UnableToFindException {
        // prepare
        reset();
        User user = userCreator.getEntity(0);
        doThrow(new UnableToFindException()).when(userDao).findClientByEmail(user.getEmail());

        try {
            // do
            userService.getClientByEmail(user.getEmail());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);
            verify(userDao, Mockito.times(1)).findClientByEmail(user.getEmail());

            return;
        }
        Assert.fail();
    }

}
