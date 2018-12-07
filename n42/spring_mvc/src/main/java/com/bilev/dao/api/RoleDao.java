package com.bilev.dao.api;

import com.bilev.exception.dao.UnableToFindException;
import com.bilev.model.Role;

public interface RoleDao {
    Role getRoleByName(Role.RoleName roleName) throws UnableToFindException;
}
