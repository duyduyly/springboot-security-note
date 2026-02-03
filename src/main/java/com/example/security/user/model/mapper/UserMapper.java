package com.example.security.user.model.mapper;

import com.example.security.auth.model.entity.Role;
import com.example.security.user.model.entity.User;
import com.example.security.auth.model.request.SignupRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(SignupRequest signUpRequest, Role roles) {
        return User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .active(true)
                .role(roles)
                .build();
    }
}
