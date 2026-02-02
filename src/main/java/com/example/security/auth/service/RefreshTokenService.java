package com.example.security.auth.service;

import com.example.security.auth.model.entity.RefreshToken;
import com.example.security.auth.model.request.TokenRefreshRequest;
import com.example.security.auth.model.response.JwtRefreshResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Authentication authentication);
    ResponseEntity<JwtRefreshResponse> refreshToken(TokenRefreshRequest request);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteExpiredTokens();
}
