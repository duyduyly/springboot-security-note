package com.example.security.auth.model.entity;

import com.example.security.auth.model.audit.OtpAuditListener;
import com.example.security.auth.model.enums.OtpTypeEnum;
import com.example.security.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@EntityListeners(OtpAuditListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp {

    @Id
    @Column(length = 200)
    private String uuid;

    @Column(name = "otp_hash", length = 200)
    private String otpHash;

    @Column(length = 30, nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private OtpTypeEnum type;

    private LocalDateTime expiresAt;

    @Builder.Default
    private Integer attemptsCount = 0;

    private Integer maxAttempts;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
