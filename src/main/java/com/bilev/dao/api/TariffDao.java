package com.bilev.dao.api;

import com.bilev.model.Tariff;

import java.util.List;

public interface TariffDao {
    void saveOrUpdate(Tariff tariff);

    void remove(Tariff tariff);

    List<Tariff> getAllTariffs();
    Tariff findById(int id);
}
