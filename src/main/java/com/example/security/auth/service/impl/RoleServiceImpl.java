package com.example.security.auth.service.impl;

import com.example.security.auth.model.entity.Permission;
import com.example.security.auth.model.entity.Role;
import com.example.security.auth.model.entity.RolePermission;
import com.example.security.auth.model.enums.RoleEnum;
import com.example.security.auth.repository.PermissionRepository;
import com.example.security.auth.repository.RolePermissionRepository;
import com.example.security.auth.repository.RoleRepository;
import com.example.security.auth.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    private Map<String, List<Permission>> rolePermissionMap;

    @Override
    @PostConstruct
    public void loadRolesAndPermissions() {

    }

    @Override
    public void refreshRolesAndPermissions() {
//        Optional<Role> userRoleOp = roleRepository.findByName(RoleEnum.USER);
//        Optional<Role> reviewerRoleOp = roleRepository.findByName(RoleEnum.REVIEWER);
//        Optional<Role> mentorRoleOp = roleRepository.findByName(RoleEnum.MENTOR);
//        Optional<Role> adminRoleOp = roleRepository.findByName(RoleEnum.ADMIN);
//
//        if (userRoleOp.isEmpty()) throw new RuntimeException("USER role not found");
//        if (reviewerRoleOp.isEmpty()) throw new RuntimeException("Reviewer role not found");
//        if (mentorRoleOp.isEmpty()) throw new RuntimeException("Mentor role not found");
//        if (adminRoleOp.isEmpty()) throw new RuntimeException("Admin role not found");
//
//        List<RolePermission> userRolePermission = rolePermissionRepository.findByRoleId(userRoleOp.get().getId());
//        List<RolePermission> reviewerRolePermission = rolePermissionRepository.findByRoleId(reviewerRoleOp.get().getId());
//        List<RolePermission> mentorRolePermission = rolePermissionRepository.findByRoleId(mentorRoleOp.get().getId());
//        List<RolePermission> adminRolePermission = rolePermissionRepository.findByRoleId(adminRoleOp.get().getId());
    }

    @Override
    public Map<String, List<Permission>> getRole(String roleName) {
        return Map.of();
    }


}
