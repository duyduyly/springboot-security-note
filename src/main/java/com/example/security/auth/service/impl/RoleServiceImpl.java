package com.example.security.auth.service.impl;

import com.example.security.auth.model.entity.Permission;
import com.example.security.auth.model.enums.RoleEnum;
import com.example.security.auth.repository.PermissionRepository;
import com.example.security.auth.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final PermissionRepository permissionRepository;
    private Map<RoleEnum, List<Permission>> permissionMap;

    @Override
    @PostConstruct
    public void loadRolesAndPermissions() {
        refreshRolesAndPermissions();
    }

    @Override
    public void refreshRolesAndPermissions() {
        if(Objects.isNull(permissionMap)){
            permissionMap = new HashMap<>();
        }else{
            permissionMap.clear();
        }

        List<Permission> adminPermissionList = permissionRepository.findPermissionsByRoleName(RoleEnum.ADMIN);
        List<Permission> userPermissionList = permissionRepository.findPermissionsByRoleName(RoleEnum.USER);
        List<Permission> reviewerPermissionList = permissionRepository.findPermissionsByRoleName(RoleEnum.REVIEWER);
        List<Permission> mentorPermissionList = permissionRepository.findPermissionsByRoleName(RoleEnum.MENTOR);

        permissionMap.put(RoleEnum.ADMIN, adminPermissionList);
        permissionMap.put(RoleEnum.USER, userPermissionList);
        permissionMap.put(RoleEnum.REVIEWER, reviewerPermissionList);
        permissionMap.put(RoleEnum.MENTOR, mentorPermissionList);
    }

    @Override
    public List<Permission> getPermissionList(@NonNull RoleEnum roleName) {
        if(!permissionMap.containsKey(roleName)) return new ArrayList<>();
        return this.permissionMap.get(roleName);
    }


}
