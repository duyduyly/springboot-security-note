package com.example.security.model.mapper;

import com.example.security.model.entity.Role;
import com.example.security.model.entity.User;
import com.example.security.model.request.SignupRequest;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {

    public static User mapWithSignRequest(SignupRequest signUpRequest, Set<Role> roles) {
       return User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .roles(roles)
                .build();
    }
}
