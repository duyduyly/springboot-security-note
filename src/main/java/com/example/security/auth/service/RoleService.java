package com.example.security.auth.service;

import com.example.security.auth.model.entity.Permission;

import java.util.List;
import java.util.Map;

public interface RoleService {

    void loadRolesAndPermissions();
    void refreshRolesAndPermissions();
    Map<String, List<Permission>> getRole(String roleName);


}
