package com.bilev.dao.api;

import com.bilev.model.Tariff;

import java.util.List;

public interface TariffDao extends AbstractDao<Integer, Tariff> {

    List<Tariff> getAllTariffs();

}
