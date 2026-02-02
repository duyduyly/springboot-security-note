package com.example.security.auth.model.entity;

import com.example.security.auth.model.audit.OtpAuditListener;
import com.example.security.user.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "otp_code")
@EntityListeners(OtpAuditListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String otpKey;

    private String otpCode;

    @OneToOne
    private User user;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    public boolean isExpired() {
        return expiryDate.isBefore(Instant.now());
    }

}
