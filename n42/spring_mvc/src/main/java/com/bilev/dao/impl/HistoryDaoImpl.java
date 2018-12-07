package com.bilev.dao.impl;

import com.bilev.dao.api.HistoryDao;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.History;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("historyDao")
public class HistoryDaoImpl extends AbstractDaoImpl<Integer, History> implements HistoryDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<History> getContractHistory(int contractId) throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.createAlias("contract", "contract")
                    .add(Restrictions.eq("contract.id", contractId));
            return (List<History>) criteria.list();

        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

}
