package com.example.security.auth.config;

import com.example.security.common.model.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
@Slf4j
public class CustomAccessDeniedHandler
        implements AccessDeniedHandler { //handle 403 Forbidden errors

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        CommonResponse commonResponse = CommonResponse.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .message(accessDeniedException.getMessage())
                .data(new ArrayList<>())
                .build();

        objectMapper.writeValue(response.getOutputStream(), commonResponse);
    }
}
