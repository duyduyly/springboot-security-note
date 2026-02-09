package com.example.security.auth.config;

import com.example.security.common.model.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint { // to handle 401 Unauthorized error

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        CommonResponse commonResponse = CommonResponse.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("Authentication required or token is invalid")
                .data(new ArrayList<>())
                .build();

        objectMapper.writeValue(response.getOutputStream(), commonResponse);
    }
}
