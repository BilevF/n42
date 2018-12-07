package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.Tariff;

import java.util.List;

public interface TariffDao extends AbstractDao<Integer, Tariff> {

    List<Tariff> getAllTariffs() throws UnableToFindException;

    List<Tariff> getAvailableTariffs() throws UnableToFindException;

    Tariff getTariffByName(String name) throws UnableToFindException;

}
