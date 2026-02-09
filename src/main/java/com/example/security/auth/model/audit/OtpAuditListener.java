package com.example.security.auth.model.audit;


import com.example.security.auth.model.entity.Otp;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class OtpAuditListener {

    @Value("${security.otp.ExpirationM}")
    private int otpExpirationM;

    @Value("${security.otp.maxAttempts}")
    private int maxAttempts;

    @PrePersist
    public void prePersist(Otp value) {
        value.setUuid(UUID.randomUUID().toString());
        value.setCreatedAt(LocalDateTime.now());
        value.setAttemptsCount(0);
        value.setMaxAttempts(maxAttempts);
        value.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpirationM));
    }

}
