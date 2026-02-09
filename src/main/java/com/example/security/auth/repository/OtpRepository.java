package com.example.security.auth.repository;

import com.example.security.auth.model.entity.Otp;
import com.example.security.auth.model.enums.OtpTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUser_EmailAndType(String email, OtpTypeEnum otpType);
    List<Otp> findByExpiresAtLessThan(LocalDateTime time);
}
