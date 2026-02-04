package com.example.security.auth.controller;


import com.example.security.auth.model.request.LoginRequest;
import com.example.security.auth.model.request.SignupRequest;
import com.example.security.auth.model.request.TokenRefreshRequest;
import com.example.security.auth.model.request.VerifyOtpRequest;
import com.example.security.auth.model.response.JwtResponse;
import com.example.security.auth.service.AuthService;
import com.example.security.auth.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final RefreshTokenService refreshTokenService;


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    JwtResponse jwtResponse = authService.signIn(loginRequest);
    return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
    return authService.signUp(request);
  }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        return authService.verifyOtp(request);
    }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
      return refreshTokenService.refreshToken(request);
  }

}
