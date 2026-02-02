package com.example.security.user.repository;

import com.example.security.user.model.entity.Profile;
import com.example.security.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, User> {
    Optional<Profile> findById(Long userId);
}
