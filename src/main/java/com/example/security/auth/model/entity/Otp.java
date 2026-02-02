package com.example.security.auth.model.entity;

import com.example.security.auth.model.audit.OtpAuditListener;
import com.example.security.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@EntityListeners(OtpAuditListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    @Id
    @Column(length = 200)
    private String uuid;

    @Column(length = 6)
    private String otpCode;

    @Column(length = 300)
    private String token;

    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
