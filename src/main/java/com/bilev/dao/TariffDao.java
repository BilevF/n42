package com.bilev.dao;

import com.bilev.model.Tariff;

import java.util.List;

public interface TariffDao {
    void saveTariff(Tariff tariff);

    List<Tariff> getAllTariffs();
    Tariff findById(int id);
}
