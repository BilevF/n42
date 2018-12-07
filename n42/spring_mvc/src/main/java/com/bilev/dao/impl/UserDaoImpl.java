package com.bilev.dao.impl;

import com.bilev.dao.api.UserDao;
import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.Role;
import com.bilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<Integer, User> implements UserDao {

    @Override
    public User findByEmail(String email) throws UnableToFindException {
        try {
            if (email == null) throw new UnableToFindException("email == null");

            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("email", email));

            return (User) criteria.uniqueResult();

        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    @Override
    public User findClientByEmail(String email) throws UnableToFindException {
        try {
            if (email == null) throw new UnableToFindException("email == null");

            Criteria criteria = createEntityCriteria();

            criteria
                    .createAlias("role", "role")
                    .add(Restrictions.eq("role.roleName", Role.RoleName.ROLE_CLIENT))
                    .add(Restrictions.eq("email", email));

            return (User) criteria.uniqueResult();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

    @Override
    public User getClientById(int id) throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();

            criteria
                    .createAlias("role", "role")
                    .add(Restrictions.eq("role.roleName", Role.RoleName.ROLE_CLIENT))
                    .add(Restrictions.eq("id", id));

            return (User) criteria.uniqueResult();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllClients() throws UnableToFindException {
        try {
            Criteria criteria = createEntityCriteria();

            criteria
                    .createAlias("role", "role")
                    .add(Restrictions.eq("role.roleName", Role.RoleName.ROLE_CLIENT));

            return (List<User>) criteria.list();
        } catch (Exception ex) {
            throw new UnableToFindException(ex);
        }
    }

}
