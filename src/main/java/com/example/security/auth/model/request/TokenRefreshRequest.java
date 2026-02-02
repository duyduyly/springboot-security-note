package com.example.security.auth.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRefreshRequest {
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String username;
}
