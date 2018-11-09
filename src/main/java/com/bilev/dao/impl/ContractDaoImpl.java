package com.bilev.dao.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.exception.NotFoundException;
import com.bilev.exception.UnableToUpdateException;
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
    public List<Contract> getUserContracts(int userId) {
        Criteria criteria = createEntityCriteria();
        criteria.createAlias("user", "user")
                .add(Restrictions.eq("user.Id", userId));
        return (List<Contract>) criteria.list();
    }

    @Override
    public Contract getContractByPhone(String phone) throws NotFoundException {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("phoneNumber", phone));
        Contract contract = (Contract) criteria.uniqueResult();
        if (contract == null) throw new NotFoundException("Contract not found");
        return contract;
    }

    @Override
    public void updateAll(Collection<Contract> contracts) throws UnableToUpdateException {
        for (Contract contract : contracts)
            update(contract);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Contract> getAllContractsWithOption(int optionId) {

        Criteria criteria = createEntityCriteria();
        criteria.createAlias("options", "options")
                .add(Restrictions.eq("options.id", optionId));

        List<Contract> res = new ArrayList<>(criteria.list());

        criteria = createEntityCriteria();
        criteria.createAlias("basket", "basket")
                .add(Restrictions.eq("basket.id", optionId));

        res.addAll(criteria.list());
        return res;
    }
}
