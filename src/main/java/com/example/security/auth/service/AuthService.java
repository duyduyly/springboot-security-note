package com.example.security.auth.service;

import com.example.security.auth.model.request.LoginRequest;
import com.example.security.auth.model.request.SignupRequest;
import com.example.security.auth.model.response.JwtResponse;

public interface AuthService {
    JwtResponse signIn(LoginRequest loginRequest);
    void signUp(SignupRequest signUpRequest) throws NoSuchFieldException;
}
