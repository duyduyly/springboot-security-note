package com.example.security.repositories;

import com.example.security.model.entity.Profile;
import com.example.security.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, User> {
    Optional<Profile> findById(Long userId);
}
