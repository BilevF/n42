package com.bilev.dao.impl;

import com.bilev.dao.api.TariffDao;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.Tariff;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tariffDao")
public class TariffDaoImpl extends AbstractDaoImpl<Integer, Tariff> implements TariffDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Tariff> getAllTariffs() throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            return (List<Tariff>) criteria.list();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tariff> getAvailableTariffs() throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("valid", true));
            return (List<Tariff>) criteria.list();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    @Override
    public Tariff getTariffByName(String name) throws UnableToFindException {
        try {
            if (name == null) throw new UnableToFindException("name == null");

            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("name", name));

            return (Tariff) criteria.uniqueResult();

        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }


}
