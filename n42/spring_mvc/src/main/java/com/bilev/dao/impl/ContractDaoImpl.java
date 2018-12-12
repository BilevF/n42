package com.bilev.dao.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.exception.dao.UnableToUpdateException;
import com.bilev.model.Contract;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository("contractDao")
public class ContractDaoImpl extends AbstractDaoImpl<Integer, Contract> implements ContractDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Contract> getUserContracts(int userId) throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.createAlias("user", "user")
                    .add(Restrictions.eq("user.id", userId));
            return (List<Contract>) criteria.list();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    @Override
    public Contract getContractByPhone(String phone) throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("phoneNumber", phone));

            return (Contract) criteria.uniqueResult();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Contract> getContractsWithTariff(int tariffId) throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.createAlias("tariff", "tariff")
                    .add(Restrictions.eq("tariff.id", tariffId));
            return (List<Contract>) criteria.list();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    @Override
    public void updateAll(Collection<Contract> contracts) throws UnableToUpdateException {
        for (Contract contract : contracts)
            update(contract);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Contract> getAllContractsWithOption(int optionId) throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.createAlias("options", "options")
                    .add(Restrictions.eq("options.id", optionId));

            List<Contract> res = new ArrayList<>(criteria.list());

            criteria = createEntityCriteria();
            criteria.createAlias("basket", "basket")
                    .add(Restrictions.eq("basket.id", optionId));

            res.addAll(criteria.list());
            return res;
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }
}
