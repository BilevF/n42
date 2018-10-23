package com.bilev.dao;

import com.bilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao  {
    @Override
    public User find(String email, String password) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("email", email));
        criteria.add(Restrictions.eq("password", password));
        return (User) criteria.uniqueResult();
    }
}
