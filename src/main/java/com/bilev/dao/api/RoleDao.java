package com.bilev.dao.api;

import com.bilev.model.Role;

public interface RoleDao {
    Role getRoleByName(Role.RoleName roleName);
}
