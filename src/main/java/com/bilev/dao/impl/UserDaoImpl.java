package com.bilev.dao.impl;

import com.bilev.dao.api.UserDao;
import com.bilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    @Override
    public User find(String email) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("email", email));
        return (User) criteria.uniqueResult();
    }

    @Override
    public User findById(int id) {
        return getByKey(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Criteria criteria = createEntityCriteria();
        return (List<User>) criteria.list();
    }
}
