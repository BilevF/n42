package com.bilev.service;

import com.bilev.model.Option;
import com.bilev.model.Tariff;

import java.util.List;

public interface TariffService {
    void saveTariff(Tariff tariff);

    List<Tariff> getAllTariffs();

    Tariff findById(int id);

    void saveOption(Option option);

    Option findOption(int id);

    void removeOption(int id);
}
