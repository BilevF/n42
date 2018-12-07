package com.bilev.service.impl;


import com.bilev.configuration.ServiceTestConfig;
import com.bilev.dao.api.ContractDao;
import com.bilev.dao.api.OptionDao;
import com.bilev.dao.api.TariffDao;
import com.bilev.dto.BasicOptionDto;
import com.bilev.dto.BasicTariffDto;
import com.bilev.dto.OptionDto;
import com.bilev.dto.TariffDto;
import com.bilev.exception.dao.UnableToFindException;

import com.bilev.exception.dao.UnableToRemoveException;
import com.bilev.exception.dao.UnableToSaveException;
import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.exception.service.OperationFailed;
import com.bilev.exception.service.ServiceErrors;
import com.bilev.model.Contract;
import com.bilev.model.Option;
import com.bilev.model.Tariff;
import com.bilev.service.api.TariffService;
import com.bilev.tools.ContractCreator;
import com.bilev.tools.OptionCreator;
import com.bilev.tools.TariffCreator;
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
public class TariffServiceImplTest implements ServiceErrors {

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private ContractDao contractDao;



    @Autowired
    TariffCreator tariffCreator;

    @Autowired
    OptionCreator optionCreator;

    @Autowired
    ContractCreator contractCreator;



    @Autowired
    TariffService tariffService;


    @Before
    public void setUp() {

    }

    private void reset() {
        Mockito.reset(tariffDao);
        Mockito.reset(optionDao);
        Mockito.reset(contractDao);
    }


    @Test
    public void testGetAllTariffs() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        List<BasicTariffDto> test = tariffCreator.getBasicDtoList();
        when(tariffDao.getAllTariffs()).thenReturn(tariffCreator.getEntityList());

        // do
        Collection<BasicTariffDto> res = tariffService.getAllTariffs();

        // verify
        Assert.assertTrue(res.containsAll(test) && test.containsAll(res));
        verify(tariffDao, Mockito.times(1)).getAllTariffs();
    }

    @Test
    public void testGetAllTariffsException() throws UnableToFindException {
        // prepare
        reset();
        doThrow(new UnableToFindException()).when(tariffDao).getAllTariffs();

        try {
            // do
            tariffService.getAllTariffs();
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);
            verify(tariffDao, Mockito.times(1)).getAllTariffs();

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetAvailableTariffsForContract() throws UnableToFindException, OperationFailed {
        // prepare
        reset();

        Contract contract = contractCreator.getEntity(0);
        contract.setTariff(tariffCreator.getEntity(0));

        List<BasicTariffDto> test = tariffCreator.getBasicDtoList();
        test.remove(tariffCreator.getBasicDto(0));

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        when(tariffDao.getAvailableTariffs()).thenReturn(tariffCreator.getEntityList());

        // do
        Collection<BasicTariffDto> res = tariffService.getAvailableTariffs(contract.getId());

        // verify
        Assert.assertTrue(res.containsAll(test) && test.containsAll(res));
        verify(tariffDao, Mockito.times(1)).getAvailableTariffs();
    }


    @Test
    public void testGetAvailableTariffsForContractNotFound() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        when(contractDao.getByKey(contract.getId())).thenReturn(null);

        try {
            // do
            tariffService.getAvailableTariffs(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), CONTRACT_NOT_FOUND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetAvailableTariffsForContractException() throws UnableToFindException {
        // prepare
        reset();
        Contract contract = contractCreator.getEntity(0);
        contract.setTariff(tariffCreator.getEntity(0));

        List<BasicTariffDto> test = tariffCreator.getBasicDtoList();
        test.remove(tariffCreator.getBasicDto(0));

        when(contractDao.getByKey(contract.getId())).thenReturn(contract);
        doThrow(new UnableToFindException()).when(tariffDao).getAvailableTariffs();

        try {
            // do
            tariffService.getAvailableTariffs(contract.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetAvailableTariffs() throws UnableToFindException, OperationFailed {
        // prepare
        reset();

        List<BasicTariffDto> test = tariffCreator.getBasicDtoList();

        when(tariffDao.getAvailableTariffs()).thenReturn(tariffCreator.getEntityList());

        // do
        Collection<BasicTariffDto> res = tariffService.getAvailableTariffs();

        // verify
        Assert.assertTrue(res.containsAll(test) && test.containsAll(res));
        verify(tariffDao, Mockito.times(1)).getAvailableTariffs();
    }

    @Test
    public void testGetAvailableTariffsException() throws UnableToFindException {
        // prepare
        reset();
        doThrow(new UnableToFindException()).when(tariffDao).getAvailableTariffs();

        try {
            // do
            tariffService.getAvailableTariffs();
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetTariff() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        // do
        TariffDto res = tariffService.getTariff(tariff.getId());

        // verify
        Assert.assertEquals(res, tariffCreator.getDto(tariff.getId()));
        verify(tariffDao, Mockito.times(1)).getByKey(tariff.getId());
        verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
    }


    @Test
    public void testGetTariffNotFound() throws UnableToFindException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);

        try {
            // do
            tariffService.getTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetTariffException() throws UnableToFindException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        doThrow(new UnableToFindException()).when(tariffDao).getByKey(tariff.getId());

        try {
            // do
            tariffService.getTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetTariffBasicOptions() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        tariff.setOptions(new HashSet<>(optionCreator.getEntityList()));

        Set<BasicOptionDto> test = new HashSet<>(optionCreator.getBasicDtoList());

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        // do
        Set<BasicOptionDto> res = tariffService.getTariffBasicOptions(tariff.getId());

        // verify
        Assert.assertTrue(res.containsAll(test) && test.containsAll(res));
        verify(tariffDao, Mockito.times(1)).getByKey(tariff.getId());
        verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
    }

    @Test
    public void testGetTariffBasicOptions_TariffNotFound() throws UnableToFindException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);

        try {
            // do
            tariffService.getTariffBasicOptions(0);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetTariffBasicOptionsException() throws UnableToFindException {
        // prepare
        reset();

        doThrow(new UnableToFindException()).when(tariffDao).getByKey(Mockito.any());

        try {
            // do
            tariffService.getTariffBasicOptions(0);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testGetOption() throws UnableToFindException, OperationFailed {
        // prepare
        reset();
        Option option = optionCreator.getEntity(0);
        when(optionDao.getByKey(option.getId())).thenReturn(option);

        // do
        OptionDto res = tariffService.getOption(option.getId());

        // verify
        Assert.assertEquals(res, optionCreator.getDto(option.getId()));
        verify(optionDao, Mockito.times(1)).getByKey(option.getId());
        verify(optionDao, Mockito.times(1)).getByKey(Mockito.any());
    }


    @Test
    public void testGetOptionNotFound() throws UnableToFindException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        when(optionDao.getByKey(option.getId())).thenReturn(null);

        try {
            // do
            tariffService.getOption(option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), OPTION_NOT_FOUND);

            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetOptionException() throws UnableToFindException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);
        doThrow(new UnableToFindException()).when(optionDao).getByKey(option.getId());

        try {
            // do
            tariffService.getOption(option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_FIND);

            return;
        }
        Assert.fail();
    }


    @Test
    public void testSaveTariff() throws OperationFailed, UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicTariffDto tariff = tariffCreator.getBasicDto(0);
        when(tariffDao.getTariffByName(tariff.getName())).thenReturn(null);

        // do
        Integer res = tariffService.saveTariff(tariff);

        // verify
        Assert.assertEquals(res, tariff.getId());
        verify(tariffDao, Mockito.times(1)).persist(Mockito.any());
    }


    @Test
    public void testSaveTariffValidation() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicTariffDto tariff = tariffCreator.getBasicDto(0);
        tariff.setName(null);

        when(tariffDao.getTariffByName(tariff.getName())).thenReturn(null);

        try {
            // do
            tariffService.saveTariff(tariff);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), VALIDATION);
            verify(tariffDao, Mockito.times(0)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveTariffNotUnique() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicTariffDto tariff = tariffCreator.getBasicDto(0);
        when(tariffDao.getTariffByName(tariff.getName())).thenReturn(tariffCreator.getEntity(0));
        doThrow(new UnableToSaveException()).when(tariffDao).persist(Mockito.any());

        try {
            // do
            tariffService.saveTariff(tariff);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NAME_NOT_UNIQUE);
            verify(tariffDao, Mockito.times(0)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveTariffException() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicTariffDto tariff = tariffCreator.getBasicDto(0);
        when(tariffDao.getTariffByName(tariff.getName())).thenReturn(null);
        doThrow(new UnableToSaveException()).when(tariffDao).persist(Mockito.any());

        try {
            // do
            tariffService.saveTariff(tariff);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_SAVE);
            verify(tariffDao, Mockito.times(1)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveTariff() throws OperationFailed, UnableToSaveException, UnableToFindException, UnableToRemoveException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        tariff.setValid(false);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        // do
        tariffService.removeTariff(tariff.getId());

        // verify
        verify(tariffDao, Mockito.times(1)).getByKey(tariff.getId());
        verify(optionDao, Mockito.times(1)).removeAll(tariff.getOptions());
        verify(tariffDao, Mockito.times(1)).delete(tariff);

        verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
        verify(optionDao, Mockito.times(1)).removeAll(Mockito.any());
        verify(tariffDao, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void testRemoveValidTariff() throws UnableToFindException, UnableToRemoveException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        try {
            // do
            tariffService.removeTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_VALID);
            verify(tariffDao, Mockito.times(1)).getByKey(tariff.getId());
            verify(optionDao, Mockito.times(0)).removeAll(Mockito.any());
            verify(tariffDao, Mockito.times(0)).delete(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveActiveTariff() throws UnableToFindException, UnableToRemoveException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        tariff.setValid(false);
        tariff.setContracts(new HashSet<>(contractCreator.getEntityList()));
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        try {
            // do
            tariffService.removeTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_HAS_USERS);
            verify(tariffDao, Mockito.times(1)).getByKey(tariff.getId());
            verify(optionDao, Mockito.times(0)).removeAll(Mockito.any());
            verify(tariffDao, Mockito.times(0)).delete(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveTariffNotFound() throws UnableToFindException, UnableToRemoveException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);

        try {
            // do
            tariffService.removeTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);
            verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(optionDao, Mockito.times(0)).removeAll(Mockito.any());
            verify(tariffDao, Mockito.times(0)).delete(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveTariffException() throws UnableToFindException, UnableToRemoveException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);
        tariff.setValid(false);
        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);
        doThrow(new UnableToRemoveException()).when(tariffDao).delete(tariff);

        try {
            // do
            tariffService.removeTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_REMOVE);
            verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(optionDao, Mockito.times(1)).removeAll(Mockito.any());
            verify(tariffDao, Mockito.times(1)).delete(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testReplaceTariff() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();
        Tariff originalTariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        originalTariff.setValid(false);

        Contract contract = contractCreator.getEntity(0);

        contract.getBasket().add(optionCreator.getEntity(0));
        contract.getOptions().add(optionCreator.getEntity(1));

        originalTariff.getContracts().add(contract);

        when(tariffDao.getByKey(originalTariff.getId())).thenReturn(originalTariff);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);

        // do
        tariffService.replaceTariff(originalTariff.getId(), replacementTariff.getId());

        // verify
        Assert.assertEquals(contract.getTariff(), replacementTariff);
        Assert.assertTrue(contract.getOptions().isEmpty());
        Assert.assertTrue(contract.getBasket().isEmpty());

        verify(contractDao, Mockito.times(1)).updateAll(originalTariff.getContracts());
        verify(contractDao, Mockito.times(1)).updateAll(Mockito.any());

    }

    @Test
    public void testReplaceTariffNotFound() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();

        Tariff originalTariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        originalTariff.setValid(false);

        when(tariffDao.getByKey(originalTariff.getId())).thenReturn(null);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(null);

        try {
            // do
            tariffService.replaceTariff(originalTariff.getId(), replacementTariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);
            verify(contractDao, Mockito.times(0)).updateAll(Mockito.any());

            return;
        }
        Assert.fail();
    }


    @Test
    public void testReplaceSameTariff() throws UnableToUpdateException {
        // prepare
        reset();

        try {
            // do
            tariffService.replaceTariff(0, 0);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);
            verify(contractDao, Mockito.times(0)).updateAll(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testReplaceValidTariff() throws UnableToUpdateException, UnableToFindException {
        // prepare
        reset();

        reset();
        Tariff originalTariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);

        when(tariffDao.getByKey(originalTariff.getId())).thenReturn(originalTariff);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);

        try {
            // do
            tariffService.replaceTariff(originalTariff.getId(), replacementTariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_VALID);
            verify(contractDao, Mockito.times(0)).updateAll(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testReplaceTariffReplacementInvalid() throws UnableToUpdateException, UnableToFindException {
        // prepare
        reset();

        reset();
        Tariff originalTariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        originalTariff.setValid(false);
        replacementTariff.setValid(false);

        when(tariffDao.getByKey(originalTariff.getId())).thenReturn(originalTariff);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);

        try {
            // do
            tariffService.replaceTariff(originalTariff.getId(), replacementTariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_UNAVAILABLE);
            verify(contractDao, Mockito.times(0)).updateAll(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testReplaceTariffException() throws UnableToUpdateException, UnableToFindException {
        // prepare
        reset();

        reset();
        Tariff originalTariff = tariffCreator.getEntity(0);
        Tariff replacementTariff = tariffCreator.getEntity(1);
        originalTariff.setValid(false);

        when(tariffDao.getByKey(originalTariff.getId())).thenReturn(originalTariff);
        when(tariffDao.getByKey(replacementTariff.getId())).thenReturn(replacementTariff);

        doThrow(new UnableToUpdateException()).when(contractDao).updateAll(originalTariff.getContracts());

        try {
            // do
            tariffService.replaceTariff(originalTariff.getId(), replacementTariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_UPDATE);
            verify(contractDao, Mockito.times(1)).updateAll(Mockito.any());

            return;
        }
        Assert.fail();
    }


    @Test
    public void testSaveOption() throws OperationFailed, UnableToFindException, UnableToUpdateException, UnableToSaveException {
        // prepare
        reset();
        BasicOptionDto option = optionCreator.getDto(0);

        BasicOptionDto requiredOption = optionCreator.getBasicDto(1);
        BasicOptionDto incompatibleOption = optionCreator.getBasicDto(2);
        BasicOptionDto nonOption = optionCreator.getBasicDto(3);

        requiredOption.setSelectedOptionType(BasicOptionDto.SelectedOptionType.REQUIRED);
        incompatibleOption.setSelectedOptionType(BasicOptionDto.SelectedOptionType.INCOMPATIBLE);
        nonOption.setSelectedOptionType(BasicOptionDto.SelectedOptionType.NON);

        when(optionDao.getByKey(requiredOption.getId())).thenReturn(optionCreator.getEntity(requiredOption.getId()));
        when(optionDao.getByKey(incompatibleOption.getId())).thenReturn(optionCreator.getEntity(incompatibleOption.getId()));
        when(optionDao.getByKey(nonOption.getId())).thenReturn(optionCreator.getEntity(nonOption.getId()));

        option.getRelatedOptions().add(requiredOption);
        option.getRelatedOptions().add(incompatibleOption);
        option.getRelatedOptions().add(nonOption);

        // do
        tariffService.saveOption(option);

        // verify
        verify(optionDao, Mockito.times(1)).getByKey(requiredOption.getId());
        verify(optionDao, Mockito.times(1)).getByKey(incompatibleOption.getId());
        verify(optionDao, Mockito.times(1)).getByKey(nonOption.getId());
        verify(optionDao, Mockito.times(1)).persist(Mockito.any());

    }

    @Test
    public void testSaveOptionValidation() throws UnableToSaveException {
        // prepare
        reset();
        BasicOptionDto option = optionCreator.getDto(0);
        option.setName(null);

        try {
            // do
            tariffService.saveOption(option);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), VALIDATION);
            verify(optionDao, Mockito.times(0)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveOptionException() throws UnableToSaveException {
        // prepare
        reset();
        BasicOptionDto option = optionCreator.getDto(0);

        doThrow(new UnableToSaveException()).when(optionDao).persist(Mockito.any());

        try {
            // do
            tariffService.saveOption(option);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_SAVE);
            verify(optionDao, Mockito.times(1)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveOptionWithoutRelatedOption() throws UnableToSaveException, UnableToFindException {
        // prepare
        reset();
        BasicOptionDto option = optionCreator.getDto(0);

        BasicOptionDto requiredOption = optionCreator.getBasicDto(1);
        requiredOption.setSelectedOptionType(BasicOptionDto.SelectedOptionType.REQUIRED);
        option.getRelatedOptions().add(requiredOption);

        when(optionDao.getByKey(requiredOption.getId())).thenReturn(null);

        try {
            // do
            tariffService.saveOption(option);
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), RELATED_OPTION_REMOVED);
            verify(optionDao, Mockito.times(0)).persist(Mockito.any());

            return;
        }
        Assert.fail();
    }


    @Test
    public void testRemoveOption() throws OperationFailed, UnableToFindException, UnableToSaveException, UnableToUpdateException, UnableToRemoveException {
        // prepare
        reset();
        Option option = optionCreator.getEntity(0);

        List<Contract> contracts = contractCreator.getEntityList();

        for (Contract contract : contracts) {
            contract.getBasket().add(option);
            contract.getOptions().add(option);
        }

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getAllContractsWithOption(option.getId())).thenReturn(contracts);

        // do
        tariffService.removeOption(option.getId());

        // verify
        for (Contract contract : contracts) {
            Assert.assertTrue(contract.getOptions().isEmpty());
            Assert.assertTrue(contract.getBasket().isEmpty());

            verify(contractDao, Mockito.times(1)).update(contract);
        }

        verify(optionDao, Mockito.times(1)).delete(Mockito.any());
        verify(optionDao, Mockito.times(1)).delete(option);
    }

    @Test
    public void testRemoveOptionNotFound() throws UnableToFindException, UnableToRemoveException, UnableToUpdateException {
        // prepare
        reset();
        Option option = optionCreator.getEntity(0);
        when(optionDao.getByKey(option.getId())).thenReturn(null);

        try {
            // do
            tariffService.removeOption(option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), OPTION_NOT_FOUND);
            verify(optionDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(optionDao, Mockito.times(0)).delete(Mockito.any());
            verify(contractDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testRemoveOptionUnableToDelete() throws UnableToFindException, UnableToRemoveException, UnableToUpdateException {
        // prepare
        reset();

        Option option = optionCreator.getEntity(0);

        List<Contract> contracts = contractCreator.getEntityList();

        when(optionDao.getByKey(option.getId())).thenReturn(option);
        when(contractDao.getAllContractsWithOption(option.getId())).thenReturn(contracts);


        doThrow(new UnableToRemoveException()).when(optionDao).delete(option);

        try {
            // do
            tariffService.removeOption(option.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), UNABLE_TO_REMOVE);
            verify(optionDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(optionDao, Mockito.times(1)).delete(Mockito.any());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testBlockTariff() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        // do
        tariffService.blockTariff(tariff.getId());

        // verify
        Assert.assertFalse(tariff.getValid());

        verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
        verify(tariffDao, Mockito.times(1)).update(tariff);
        verify(tariffDao, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    public void testBlockTariffNotFound() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);

        try {
            // do
            tariffService.blockTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);
            verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(tariffDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testBlockTariffException() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        doThrow(new UnableToUpdateException()).when(tariffDao).update(tariff);


        try {
            // do
            tariffService.blockTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify

            Assert.assertEquals(ex.getMessage(), UNABLE_TO_EDIT);
            verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(tariffDao, Mockito.times(1)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testUnblockTariff() throws OperationFailed, UnableToFindException, UnableToUpdateException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        // do
        tariffService.unblockTariff(tariff.getId());

        // verify
        Assert.assertTrue(tariff.getValid());

        verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
        verify(tariffDao, Mockito.times(1)).update(tariff);
        verify(tariffDao, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    public void testUnblockTariffNotFound() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(null);

        try {
            // do
            tariffService.unblockTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify
            Assert.assertEquals(ex.getMessage(), TARIFF_NOT_FOUND);
            verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(tariffDao, Mockito.times(0)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testUnblockTariffException() throws UnableToFindException, UnableToUpdateException {
        // prepare
        reset();
        Tariff tariff = tariffCreator.getEntity(0);

        when(tariffDao.getByKey(tariff.getId())).thenReturn(tariff);

        doThrow(new UnableToUpdateException()).when(tariffDao).update(tariff);


        try {
            // do
            tariffService.unblockTariff(tariff.getId());
        } catch (OperationFailed ex) {
            // verify

            Assert.assertEquals(ex.getMessage(), UNABLE_TO_EDIT);
            verify(tariffDao, Mockito.times(1)).getByKey(Mockito.any());
            verify(tariffDao, Mockito.times(1)).update(Mockito.any());
            return;
        }
        Assert.fail();
    }


}
