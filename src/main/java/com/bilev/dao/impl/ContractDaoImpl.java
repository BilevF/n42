package com.bilev.dao.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.model.Contract;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository("contractDao")
public class ContractDaoImpl extends AbstractDaoImpl<Integer, Contract> implements ContractDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Contract> getUserContracts(int userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("user.Id", userId));
        return (List<Contract>) criteria.list();
    }

    @Override
    public void updateAll(Collection<Contract> contracts) {
        for (Contract contract : contracts)
            update(contract);
    }
}
