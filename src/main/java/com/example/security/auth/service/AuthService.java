package com.example.security.auth.service;

import com.example.security.auth.model.request.LoginRequest;
import com.example.security.auth.model.request.SignupRequest;
import com.example.security.auth.model.response.JwtResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService {
    JwtResponse signIn(LoginRequest loginRequest);
    void signUp(SignupRequest signUpRequest) throws NoSuchFieldException, JsonProcessingException;
    void verifyOtp(String username, String otp) throws Exception;
}
