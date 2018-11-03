package com.bilev.dao.impl;

import com.bilev.dao.api.ContractDao;
import com.bilev.model.Contract;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contractDao")
public class ContractDaoImpl extends AbstractDao<Integer, Contract> implements ContractDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Contract> getUserContracts(int userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("userId", userId));
        return (List<Contract>) criteria.list();
    }

    @Override
    public Contract findById(int id) {
        return getByKey(id);
    }

    @Override
    public void updateAll(List<Contract> contracts) {
        for (Contract contract : contracts)
            update(contract);
    }
}
