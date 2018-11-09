package com.bilev.dao.impl;

import com.bilev.dao.api.UserDao;
import com.bilev.exception.NotFoundException;
import com.bilev.model.Role;
import com.bilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<Integer, User> implements UserDao {

    @Override
    public User findByEmail(String email) throws NotFoundException {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("email", email));
        User user = (User) criteria.uniqueResult();
        if (user == null) throw new NotFoundException("User not found");
        return user;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Criteria criteria = createEntityCriteria();

        criteria
                .createAlias("role", "role")
                .add(Restrictions.eq("role.roleName", Role.RoleName.ROLE_CLIENT));
        return (List<User>) criteria.list();
    }

}
