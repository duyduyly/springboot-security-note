package com.example.security.auth.scheduler;


import com.example.security.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthScheduler {

    private final RefreshTokenService refreshTokenService;

    // Run every 10 minutes
    @Scheduled(cron = "0 */10 * * * ?")
    public void deleteExpiredTokens() {
        refreshTokenService.deleteExpiredTokens();
    }
}
