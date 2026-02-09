package com.example.security.auth.service;

import com.example.security.auth.model.entity.Permission;
import com.example.security.auth.model.enums.RoleEnum;

import java.util.List;

public interface RoleService {
    void loadRolesAndPermissions();
    void refreshRolesAndPermissions();
    List<Permission> getPermissionList(RoleEnum roleName);
}
