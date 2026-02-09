package com.example.security.auth.service.impl;

import com.example.security.auth.model.entity.RefreshToken;
import com.example.security.auth.model.request.TokenRefreshRequest;
import com.example.security.auth.model.response.JwtRefreshResponse;
import com.example.security.auth.repository.RefreshTokenRepository;
import com.example.security.auth.service.RefreshTokenService;
import com.example.security.auth.util.JwtUtils;
import com.example.security.user.model.entity.User;
import com.example.security.user.repository.UserRepository;
import com.example.security.user.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expirationMs}")
    private Long refreshTokenDurationMs;

    @Override
    public RefreshToken createRefreshToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElse(new RefreshToken());
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }

    @Override
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

    @Override
    public void deleteExpiredTokens() {
        Instant now = Instant.now();
        int deleted = refreshTokenRepository.deleteByExpiryDateBefore(now);
        System.out.println("Deleted expired refresh tokens: " + deleted);
    }
}
