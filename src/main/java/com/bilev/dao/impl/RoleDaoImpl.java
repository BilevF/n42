package com.bilev.dao.impl;

import com.bilev.dao.api.RoleDao;
import com.bilev.model.Role;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDaoImpl<Integer, Role> implements RoleDao {
    @Override
    public Role getRoleByName(Role.RoleName roleName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("roleName", roleName));
        return (Role) criteria.uniqueResult();
    }
}
