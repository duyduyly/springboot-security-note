package com.example.security.service;

import com.example.security.model.entity.RefreshToken;
import com.example.security.model.entity.User;
import com.example.security.model.request.TokenRefreshRequest;
import com.example.security.model.response.JwtRefreshResponse;
import com.example.security.repositories.RefreshTokenRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.service.user.UserDetailsImpl;
import com.example.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expirationMs}")
    private Long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete old token (optional)
        refreshTokenRepository.deleteByUserId(user.getId());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(user.getId());
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }

    public ResponseEntity<JwtRefreshResponse> refreshToken(TokenRefreshRequest request) {
        String requestToken = request.getRefreshToken();

        return refreshTokenRepository.findByToken(requestToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateAccessToken(user.getUsername());
                    return ResponseEntity.ok(new JwtRefreshResponse(accessToken, request.getRefreshToken(), user.getUsername()));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    // Run every 10 minutes
    @Scheduled(cron = "0 */10 * * * ?")
    public void deleteExpiredTokens() {
        Instant now = Instant.now();
        int deleted = refreshTokenRepository.deleteByExpiryDateBefore(now);
        System.out.println("Deleted expired refresh tokens: " + deleted);
    }
}
