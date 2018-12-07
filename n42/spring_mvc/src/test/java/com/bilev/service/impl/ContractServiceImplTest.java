package com.bilev.service.impl;

import com.bilev.configuration.ServiceTestConfig;

import com.bilev.dao.api.BlockDao;
import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.HistoryDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dao.api.UserDao;
import com.bilev.dto.BasicContractDto;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.ContractDto;
import com.bilev.dto.HistoryDto;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;

import com.bilev.model.Block;
import com.bilev.model.Contract;
import com.bilev.model.Option;
import com.bilev.model.Role;
import com.bilev.model.Tariff;
import com.bilev.model.User;
import com.bilev.service.api.ContractService;
import com.bilev.tools.ContractCreator;
import com.bilev.tools.OptionCreator;
import com.bilev.tools.TariffCreator;
import com.bilev.tools.UserCreator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class ContractServiceImplTest implements ServiceErrors {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private HistoryDao historyDao;



    @Autowired
    TariffCreator tariffCreator;

    @Autowired
    OptionCreator optionCreator;

    @Autowired
    ContractCreator contractCreator;

    @Autowired
    UserCreator userCreator;



    @Autowired
    ContractService contractService;

    @Before
    public void setUp() {

    }

    private void reset() {
        Mockito.reset(tariffDao);
        Mockito.reset(optionDao);
        Mockito.reset(contractDao);
        Mockito.reset(userDao);
        Mockito.reset(blockDao);
        Mockito.reset(historyDao);
    }


    @Test
    public void testGetContract() throws UnableToFindException, OperationFailed {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        Option option1 = optionCreator.getEntity(0);
        Option option2 = optionCreator.getEntity(1);
        option2.getRequiredOptionsOf().add(option1);

        contract.getOptions().add(option1);
        contract.getOptions().add(option2);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        ContractDto test = contractCreator.getDto(contract.getId());
        BasicOptionDto optionDto1 = optionCreator.getBasicDto(option1.getId());
        BasicOptionDto optionDto2 = optionCreator.getBasicDto(option2.getId());
        optionDto1.setAvailableForRemove(true);
        optionDto2.setAvailableForRemove(false);
        test.getOptions().add(optionDto1);
        test.getOptions().add(optionDto2);

        // do
        ContractDto res = contractService.getContract(contract.getId());

        // verify

        Assert.assertTrue(option1.isAvailableForRemove());
        Assert.assertFalse(option2.isAvailableForRemove());
        Assert.assertEquals(res, test);

        verify(contractDao, Mockito.times(1)).getByKey(contract.getId());
        verify(contractDao, Mockito.times(1)).getByKey(Mockito.any());
    }

    @Test
    public void testGetContractNotFound() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            contractService.getContract(0);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetContractException() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);

        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());


        try {
            // do
            contractService.getContract(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetAvailableOptionsForContract() throws UnableToFindException, OperationFailed {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        Tariff tariff = tariffCreator.getEntity(0);

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        Option option3 = optionCreator.getEntity(3);
        Option option4 = optionCreator.getEntity(4);
        Option option5 = optionCreator.getEntity(5);
        option3.getIncompatibleOptions().add(option4);
        option5.getRequiredOptions().add(option1);

        tariff.getOptions().add(option1);
        tariff.getOptions().add(option2);
        tariff.getOptions().add(option3);
        tariff.getOptions().add(option4);
        tariff.getOptions().add(option5);

        contract.getOptions().add(option3);
        contract.getBasket().add(option2);
        contract.setTariff(tariff);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        Set<BasicOptionDto> res = contractService.getAvailableOptionsForContract(contract.getId());

        // verify

        Assert.assertTrue(res.size() == 1 && res.contains(optionCreator.getBasicDto(1)));

        verify(contractDao, Mockito.times(1)).getByKey(contract.getId());
        verify(contractDao, Mockito.times(1)).getByKey(Mockito.any());
    }


    @Test
    public void testGetAvailableOptionsForContractNotFound() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            contractService.getAvailableOptionsForContract(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetAvailableOptionsForContractException() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);

        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());

        try {
            // do
            contractService.getAvailableOptionsForContract(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetBasket() throws UnableToFindException, OperationFailed {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBasket(new HashSet<>(optionCreator.getEntityList()));

        Set<BasicOptionDto> test = new HashSet<>(optionCreator.getBasicDtoList());

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        Set<BasicOptionDto> res = contractService.getBasket(contract.getId());

        // verify

        Assert.assertTrue(test.containsAll(res) && res.containsAll(test));

        verify(contractDao, Mockito.times(1)).getByKey(contract.getId());
        verify(contractDao, Mockito.times(1)).getByKey(Mockito.any());
    }


    @Test
    public void testGetBasketNotFound() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            contractService.getBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetBasketException() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());

        try {
            // do
            contractService.getBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetContractHistory() throws UnableToFindException, OperationFailed {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        Collection<HistoryDto> res = contractService.getContractHistory(contract.getId());

        // verify

        Assert.assertTrue(res.isEmpty());

        verify(contractDao, Mockito.times(1)).getByKey(contract.getId());
        verify(contractDao, Mockito.times(1)).getByKey(Mockito.any());
    }


    @Test
    public void testGetContractHistoryNotFound() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            contractService.getContractHistory(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetContractHistoryException() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());

        try {
            // do
            contractService.getContractHistory(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveContract() throws OperationFailed, UnableToSaveException, UnableToFindException {
        // prepare
        reset();

        BasicContractDto contractDto = contractCreator.getBasicDto(0);
        Tariff tariff = tariffCreator.getEntity(0);
        Block block = contractCreator.getBlock(Block.BlockType.NON);

        contractDto.setTariff(tariffCreator.getBasicDto(tariff.getId()));

        when(contractDao.getContractByPhone(contractDto.getPhoneNumber())).thenReturn(null);
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        // do
        Integer res = contractService.saveContract(contractDto);

        // verify

        Assert.assertEquals(res, contractDto.getId());

        verify(contractDao, Mockito.times(1)).persist(Mockito.any());
    }

    @Test
    public void testSaveContractValidation() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicContractDto contractDto = contractCreator.getBasicDto(0);
        Tariff tariff = tariffCreator.getEntity(0);
        Block block = contractCreator.getBlock(Block.BlockType.NON);

        contractDto.setTariff(tariffCreator.getBasicDto(tariff.getId()));

        when(contractDao.getContractByPhone(contractDto.getPhoneNumber())).thenReturn(null);
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        contractDto.setUserId(null);

        try {
            // do
            contractService.saveContract(contractDto);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), VALIDATION);
            verify(contractDao, Mockito.times(0)).persist(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveContractTariffNotFound() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicContractDto contractDto = contractCreator.getBasicDto(0);
        Tariff tariff = tariffCreator.getEntity(0);
        Block block = contractCreator.getBlock(Block.BlockType.NON);

        contractDto.setTariff(tariffCreator.getBasicDto(tariff.getId()));

        when(contractDao.getContractByPhone(contractDto.getPhoneNumber())).thenReturn(null);
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);

        try {
            // do
            contractService.saveContract(contractDto);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);
            verify(contractDao, Mockito.times(0)).persist(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveContractTariffNotUnique() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicContractDto contractDto = contractCreator.getBasicDto(0);
        Tariff tariff = tariffCreator.getEntity(0);
        Block block = contractCreator.getBlock(Block.BlockType.NON);

        contractDto.setTariff(tariffCreator.getBasicDto(tariff.getId()));

        when(contractDao.getContractByPhone(contractDto.getPhoneNumber()))
                .thenReturn(contractCreator.getEntity(1));
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        try {
            // do
            contractService.saveContract(contractDto);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), PHONE_NOT_UNIQUE);
            verify(contractDao, Mockito.times(0)).persist(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveContractException() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();

        BasicContractDto contractDto = contractCreator.getBasicDto(0);
        Tariff tariff = tariffCreator.getEntity(0);
        Block block = contractCreator.getBlock(Block.BlockType.NON);

        contractDto.setTariff(tariffCreator.getBasicDto(tariff.getId()));

        when(contractDao.getContractByPhone(contractDto.getPhoneNumber())).thenReturn(null);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);
        doThrow(new UnableToFindException()).when(blockDao).getBlockByType(Block.BlockType.NON);


        try {
            // do
            contractService.saveContract(contractDto);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_SAVE);
            verify(contractDao, Mockito.times(0)).persist(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testChangeContractTariff() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff tariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        replacementTariff.setValid(true);

        contract.setTariff(tariff);
        contract.getBasket().addAll(optionCreator.getEntityList());
        contract.getOptions().addAll(optionCreator.getEntityList());

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        contractService.changeContractTariff(contract.getId(), replacementTariff.getId());

        // verify

        Assert.assertEquals(contract.getTariff(), replacementTariff);
        Assert.assertTrue(contract.getBasket().isEmpty());
        Assert.assertTrue(contract.getOptions().isEmpty());

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }


    @Test
    public void testChangeContractTariffBlocked() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff tariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));
        replacementTariff.setValid(true);

        contract.setTariff(tariff);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.changeContractTariff(contract.getId(), replacementTariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testChangeContractTariffInvalid() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff tariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        replacementTariff.setValid(false);

        contract.setTariff(tariff);

        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.changeContractTariff(contract.getId(), replacementTariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_UNAVAILABLE);
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testChangeContractTariffSame() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff tariff = tariffCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        tariff.setValid(true);

        contract.setTariff(tariff);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.changeContractTariff(contract.getId(), tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testChangeContractTariffNotFound() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff tariff = tariffCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        tariff.setValid(true);

        contract.setTariff(tariff);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);

        try {
            // do
            contractService.changeContractTariff(contract.getId(), tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testChangeContractNotFound() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff tariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        replacementTariff.setValid(false);

        contract.setTariff(tariff);

        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);
        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            contractService.changeContractTariff(contract.getId(), replacementTariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testChangeContractTariffException() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff tariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        replacementTariff.setValid(true);

        contract.setTariff(tariff);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        doThrow(new UnableToUpdateException()).when(contractDao).update(contract);

        try {
            // do
            contractService.changeContractTariff(contract.getId(), tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testAddOptionToBasket() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        contractService.addOptionToBasket(contract.getId(), option.getId());

        // verify

        Assert.assertTrue(contract.getBasket().contains(option));

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }

    @Test
    public void testAddOptionToBasket_OptionNotFound() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option);

        contract.setTariff(tariff);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(optionDao.getByKey(option.getId())).thenReturn(null);

        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), OPTION_NOT_FOUND);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testAddOptionToBasket_ContractNotFound() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option.getId())).thenReturn(option);

        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testAddOptionToBasket_ContractBlocked() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);


        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testAddOptionToBasket_Contains() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option);

        contract.setTariff(tariff);
        contract.getOptions().add(option);

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);


        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), OPTION_ALREADY_ADDED);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddOptionToBasket_NotInTariff() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Tariff tariff = tariffCreator.getEntity(0);
        //tariff.getOptions().add(option);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);


        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testAddOptionToBasket_IncompatibleOption_1() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        option2.getIncompatibleOptions().add(option1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        contract.getBasket().add(option1);

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option1);
        tariff.getOptions().add(option2);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option2.getId())).thenReturn(option2);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);


        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option2.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testAddOptionToBasket_IncompatibleOption_2() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        option2.getIncompatibleOptionsOf().add(option1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        contract.getBasket().add(option1);

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option1);
        tariff.getOptions().add(option2);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option2.getId())).thenReturn(option2);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);


        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option2.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddOptionToBasket_IncompatibleOption_3() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        option2.getIncompatibleOptions().add(option1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        contract.getOptions().add(option1);

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option1);
        tariff.getOptions().add(option2);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option2.getId())).thenReturn(option2);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);


        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option2.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testAddOptionToBasket_IncompatibleOption_4() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        option2.getIncompatibleOptionsOf().add(option1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        contract.getOptions().add(option1);

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option1);
        tariff.getOptions().add(option2);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option2.getId())).thenReturn(option2);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);


        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option2.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddOptionToBasket_RequiredOption() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        option2.getRequiredOptions().add(option1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Tariff tariff = tariffCreator.getEntity(0);
        tariff.getOptions().add(option1);
        tariff.getOptions().add(option2);

        contract.setTariff(tariff);

        when(optionDao.getByKey(option2.getId())).thenReturn(option2);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.addOptionToBasket(contract.getId(), option2.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testRemoveOptionFromBasket() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        option1.getRequiredOptionsOf().add(option2);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getBasket().add(option1);
        contract.getBasket().add(option2);

        when(optionDao.getByKey(option1.getId())).thenReturn(option1);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        contractService.removeOptionFromBasket(contract.getId(), option1.getId());

        // verify
        Assert.assertTrue(contract.getBasket().isEmpty());

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }

    @Test
    public void testRemoveOptionFromBasket_Blocked() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option = optionCreator.getEntity(1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        contract.getBasket().add(option);

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.removeOptionFromBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveOptionFromBasket_OptionNotFound() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option = optionCreator.getEntity(1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getBasket().add(option);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        doThrow(new UnableToFindException()).when(optionDao).getByKey(option.getId());

        try {
            // do
            contractService.removeOptionFromBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_REMOVE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testRemoveOptionFromBasket_ContractNotFound() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option = optionCreator.getEntity(1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getBasket().add(option);

        when(optionDao.getByKey(option.getId())).thenReturn(option);

        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());

        try {
            // do
            contractService.removeOptionFromBasket(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_REMOVE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testRemoveOptionFromContract() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option = optionCreator.getEntity(1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getOptions().add(option);

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        contractService.removeOptionFromContract(contract.getId(), option.getId());

        // verify
        Assert.assertTrue(contract.getOptions().isEmpty());

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }

    @Test
    public void testRemoveOptionFromContract_Required() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option1 = optionCreator.getEntity(1);
        Option option2 = optionCreator.getEntity(2);
        option1.getRequiredOptionsOf().add(option2);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getOptions().add(option1);
        contract.getOptions().add(option2);

        when(optionDao.getByKey(option1.getId())).thenReturn(option1);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.removeOptionFromContract(contract.getId(), option1.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_REMOVE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveOptionFromContract_Blocked() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option = optionCreator.getEntity(1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        contract.getBasket().add(option);

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.removeOptionFromContract(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveOptionFromContract_OptionNotFound() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option = optionCreator.getEntity(1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getBasket().add(option);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        doThrow(new UnableToFindException()).when(optionDao).getByKey(option.getId());

        try {
            // do
            contractService.removeOptionFromContract(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_REMOVE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testRemoveOptionFromContract_ContractNotFound() throws UnableToFindException, UnableToUpdateException, OperationFailed {
        // prepare
        reset();

        Option option = optionCreator.getEntity(1);

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getOptions().add(option);

        when(optionDao.getByKey(option.getId())).thenReturn(option);

        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());

        try {
            // do
            contractService.removeOptionFromContract(contract.getId(), option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_REMOVE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSubmitBasket() throws UnableToFindException, UnableToUpdateException, OperationFailed, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);

        List<Option> options = optionCreator.getEntityList();
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getBasket().addAll(options);
        contract.setBalance(1.0);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        contractService.submitBasket(contract.getId());

        // verify
        Assert.assertTrue(contract.getBasket().isEmpty());

        Assert.assertTrue(contract.getOptions().containsAll(options) && options.containsAll(contract.getOptions()));

        verify(historyDao, Mockito.times(options.size())).persist(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(Mockito.any());
    }


    @Test
    public void testSubmitBasket_NoMoney() throws UnableToFindException, UnableToUpdateException, OperationFailed, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);

        List<Option> options = optionCreator.getEntityList();
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getBasket().addAll(options);
        contract.setBalance(-1.0);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.submitBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), NOT_ENOUGH_MONEY);

            verify(historyDao, Mockito.times(0)).persist(Mockito.any());
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSubmitBasket_Blocked() throws UnableToFindException, UnableToUpdateException, OperationFailed, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);

        List<Option> options = optionCreator.getEntityList();
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        contract.getBasket().addAll(options);

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.submitBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);

            verify(historyDao, Mockito.times(0)).persist(Mockito.any());
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSubmitBasket_NotFound() throws UnableToFindException, UnableToUpdateException, OperationFailed, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);

        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());

        try {
            // do
            contractService.submitBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_SUBMIT);

            verify(historyDao, Mockito.times(0)).persist(Mockito.any());
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testClearBasket() throws UnableToUpdateException, OperationFailed, UnableToFindException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        contract.getBasket().addAll(optionCreator.getEntityList());

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        contractService.clearBasket(contract.getId());

        Assert.assertTrue(contract.getBasket().isEmpty());

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }

    @Test
    public void testClearBasket_NotFound() throws UnableToUpdateException, UnableToFindException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            contractService.clearBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testClearBasket_Exception() throws UnableToUpdateException, UnableToFindException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        doThrow(new UnableToUpdateException()).when(contractDao).update(contract);

        try {
            // do
            contractService.clearBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(1)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testClearBasket_Blocked() throws UnableToUpdateException, UnableToFindException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.clearBasket(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testBlockContract_Admin() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_ADMIN));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Block block = contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK);


        when(userDao.getByKey(user.getId())).thenReturn(user);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.ADMIN_BLOCK)).thenReturn(block);
        when(blockDao.getBlockByType(Block.BlockType.CLIENT_BLOCK)).thenReturn(block);


        // do
        contractService.blockContract(user.getId(), contract.getId());

        // verify
        Assert.assertEquals(contract.getBlock(), block);

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }


    @Test
    public void testBlockContract_Client() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_CLIENT));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Block block = contractCreator.getBlock(Block.BlockType.CLIENT_BLOCK);


        when(userDao.getByKey(user.getId())).thenReturn(user);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.CLIENT_BLOCK)).thenReturn(block);
        when(blockDao.getBlockByType(Block.BlockType.ADMIN_BLOCK)).thenReturn(block);

        // do
        contractService.blockContract(user.getId(), contract.getId());

        // verify
        Assert.assertEquals(contract.getBlock(), block);

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }

    @Test
    public void testBlockContract_UserNotFound() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_CLIENT));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Block block = contractCreator.getBlock(Block.BlockType.CLIENT_BLOCK);


        doThrow(new UnableToFindException()).when(userDao).getByKey(user.getId());
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.CLIENT_BLOCK)).thenReturn(block);

        try {
            // do
            contractService.blockContract(user.getId(), contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();

    }

    @Test
    public void testBlockContract_ContractNotFound() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_CLIENT));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Block block = contractCreator.getBlock(Block.BlockType.CLIENT_BLOCK);


        doThrow(new UnableToFindException()).when(userDao).getByKey(user.getId());
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.CLIENT_BLOCK)).thenReturn(block);

        try {
            // do
            contractService.blockContract(user.getId(), contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();

    }

    @Test
    public void testUnblockContract_Client() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_CLIENT));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.CLIENT_BLOCK));

        Block block = contractCreator.getBlock(Block.BlockType.NON);


        when(userDao.getByKey(user.getId())).thenReturn(user);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);

        // do
        contractService.unblockContract(user.getId(), contract.getId());

        // verify
        Assert.assertEquals(contract.getBlock(), block);

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }

    @Test
    public void testUnblockContract_ClientAccess() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_CLIENT));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        Block block = contractCreator.getBlock(Block.BlockType.NON);


        when(userDao.getByKey(user.getId())).thenReturn(user);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);

        try {
            // do
            contractService.unblockContract(user.getId(), contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testUnblockContract_Admin() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_ADMIN));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        Block block = contractCreator.getBlock(Block.BlockType.NON);


        when(userDao.getByKey(user.getId())).thenReturn(user);
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);

        // do
        contractService.unblockContract(user.getId(), contract.getId());

        // verify
        Assert.assertEquals(contract.getBlock(), block);

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);
    }

    @Test
    public void testUnblockContract_ClientNotFound() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_ADMIN));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        Block block = contractCreator.getBlock(Block.BlockType.NON);


        doThrow(new UnableToFindException()).when(userDao).getByKey(user.getId());
        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);

        try {
            // do
            contractService.unblockContract(user.getId(), contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testUnblockContract_ContractNotFound() throws UnableToUpdateException, UnableToFindException, OperationFailed {
        // prepare
        reset();

        User user = userCreator.getEntity(0);
        user.setRole(userCreator.getRole(Role.RoleName.ROLE_ADMIN));

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        Block block = contractCreator.getBlock(Block.BlockType.NON);



        when(userDao.getByKey(user.getId())).thenReturn(user);
        doThrow(new UnableToFindException()).when(contractDao).getByKey(contract.getId());
        when(blockDao.getBlockByType(Block.BlockType.NON)).thenReturn(block);

        try {
            // do
            contractService.unblockContract(user.getId(), contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddMoney() throws UnableToUpdateException, UnableToFindException, OperationFailed, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Double amount = 10.0;

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        // do
        contractService.addMoney(contract.getId(), amount);

        // verify
        Assert.assertEquals(contract.getBalance(), amount);

        verify(contractDao, Mockito.times(1)).update(Mockito.any());
        verify(contractDao, Mockito.times(1)).update(contract);

        verify(historyDao, Mockito.times(1)).persist(Mockito.any());
    }


    @Test
    public void testAddMoney_Blocked() throws UnableToUpdateException, UnableToFindException, OperationFailed, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.ADMIN_BLOCK));

        Double amount = 10.0;

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.addMoney(contract.getId(), amount);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), ACCESS_DENIED);

            verify(historyDao, Mockito.times(0)).persist(Mockito.any());
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddMoney_InvalidSum() throws UnableToUpdateException, UnableToFindException, OperationFailed, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Double amount = -10.0;

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);

        try {
            // do
            contractService.addMoney(contract.getId(), amount);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);

            verify(historyDao, Mockito.times(0)).persist(Mockito.any());
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddMoney_ContractNotFound() throws UnableToUpdateException, UnableToFindException, UnableToSaveException {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setBlock(contractCreator.getBlock(Block.BlockType.NON));

        Double amount = 10.0;

        when(contractDao.getByKey(contract.getId())).thenReturn(null);
        try {
            // do
            contractService.addMoney(contract.getId(), amount);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            verify(historyDao, Mockito.times(0)).persist(Mockito.any());
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


}
