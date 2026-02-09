package com.example.security.auth.repository;

import com.example.security.auth.model.entity.Role;
import com.example.security.auth.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);

    List<Role> findByNameIn(List<RoleEnum> name);
}
