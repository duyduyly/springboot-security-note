package com.example.security.auth.repository;

import com.example.security.auth.model.RolePermissions;
import com.example.security.auth.model.entity.Role;
import com.example.security.auth.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);

//    @Query(
//            "SELECT new com.example.security.auth.model.RolePermissions(r.name, p) " +
//                    "FROM Role r " +
//                    "JOIN r.permissions p " +
//                    "WHERE r.name = :name"
//    )
//    Optional<RolePermissions> findRolePermissionsByName(RoleEnum name);


}
