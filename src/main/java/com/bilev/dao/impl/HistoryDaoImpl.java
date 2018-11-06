package com.bilev.dao.impl;

import com.bilev.dao.api.HistoryDao;
import com.bilev.model.History;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("historyDao")
public class HistoryDaoImpl extends AbstractDaoImpl<Integer, History> implements HistoryDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<History> getUserHistory(int userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("user.Id", userId));
        return (List<History>) criteria.list();
    }

}
