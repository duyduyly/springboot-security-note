package com.example.security.auth.service;

import com.example.security.auth.model.entity.Otp;
import com.example.security.auth.model.request.LoginRequest;
import com.example.security.auth.model.request.SignupRequest;
import com.example.security.auth.model.request.VerifyOtpRequest;
import com.example.security.auth.model.response.JwtResponse;
import com.example.security.common.model.response.MessageResponse;
import com.example.security.user.model.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class AuthService {

    public abstract JwtResponse signIn(LoginRequest loginRequest);

    @Transactional
    public ResponseEntity<?> signUp(SignupRequest request) {
        try {
            this.checkEmailAndUsernameExist(request);
            User user = this.saveUserProfile(request);
            String otpCode = this.generateAndSaveOtpCode(user);
            this.notifyUserSignup(request, otpCode);
        } catch (Exception e) {
            log.error("User SignUp Failed: {}", e.toString());
            return ResponseEntity.badRequest().body(new MessageResponse("User SignUp Failed, please try again!"));
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Transactional
    public ResponseEntity<?> verifyOtp(VerifyOtpRequest request){
        Otp otp = null;
        try{
            otp = this.getAndValidate(request);
            this.acceptUserOtp(otp);
            //option: notice user otp verified successfully
        }catch (Exception e){
            log.error("Verify OTP Failed: {}", e.toString());
            this.updateAttemptCount(otp);
            return ResponseEntity.badRequest().body(new MessageResponse("Verify OTP Failed, please try again!"));
        }
        return ResponseEntity.ok(new MessageResponse("Verify OTP Successfully!"));
    }

    public abstract void cleanUpExpiredOtpCodes();


    protected abstract void checkEmailAndUsernameExist(SignupRequest request) throws NoSuchFieldException;
    protected abstract User saveUserProfile(SignupRequest request);
    protected abstract String generateAndSaveOtpCode(User user) ;
    protected abstract void notifyUserSignup(SignupRequest request, String otpCode) throws JsonProcessingException;

    protected abstract Otp getAndValidate(VerifyOtpRequest request);
    protected abstract void acceptUserOtp(Otp otp);
    protected abstract void updateAttemptCount(Otp otp);
}
