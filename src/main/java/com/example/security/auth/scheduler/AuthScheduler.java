package com.example.security.auth.scheduler;


import com.example.security.auth.service.AuthService;
import com.example.security.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthScheduler {

    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;

    // Run every 10 minutes
    @Scheduled(cron = "${security.app.cleanup.token-minutes}")
    public void deleteExpiredTokens() {
        refreshTokenService.deleteExpiredTokens();
    }

    // Run every 10 minutes
    @Scheduled(cron = "${security.otp.cleanup.expiration-minutes}")
    public void deleteExpiredOtp() {
        authService.cleanUpExpiredOtpCodes();
    }

}
