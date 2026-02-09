package com.example.security.auth.repository;

import com.example.security.auth.model.entity.Permission;
import com.example.security.auth.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p FROM Permission p " +
            "JOIN RolePermission rp ON p.id = rp.permission.id " +
            "JOIN Role r ON rp.role.id = r.id " +
            "WHERE r.name = :roleName")
    List<Permission> findPermissionsByRoleName(@Param("roleName") RoleEnum roleName);
}
