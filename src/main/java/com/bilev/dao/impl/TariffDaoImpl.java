package com.bilev.dao.impl;

import com.bilev.dao.api.TariffDao;
import com.bilev.model.Tariff;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tariffDao")
public class TariffDaoImpl extends AbstractDaoImpl<Integer, Tariff> implements TariffDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Tariff> getAllTariffs() {
        Criteria criteria = createEntityCriteria();
        return (List<Tariff>) criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tariff> getAvailableTariffs() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("valid", true));
        return (List<Tariff>) criteria.list();
    }

}
