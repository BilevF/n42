package com.bilev.dao;

import com.bilev.model.Contract;
import com.bilev.model.Tariff;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tariffDao")
public class TariffDaoImpl extends AbstractDao<Integer, Tariff> implements TariffDao  {
    @Override
    public void saveTariff(Tariff tariff) {
        persist(tariff);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tariff> getAllTariffs() {
        Criteria criteria = createEntityCriteria();
        return (List<Tariff>) criteria.list();
    }

    @Override
    public Tariff findById(int id) {
        return getByKey(id);
    }
}
