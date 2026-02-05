package com.example.security.auth.service.impl;

import com.example.security.auth.model.entity.Otp;
import com.example.security.auth.model.entity.RefreshToken;
import com.example.security.auth.model.entity.Role;
import com.example.security.auth.model.enums.OtpTypeEnum;
import com.example.security.auth.model.enums.RoleEnum;
import com.example.security.auth.model.request.LoginRequest;
import com.example.security.auth.model.request.SignupRequest;
import com.example.security.auth.model.request.VerifyOtpRequest;
import com.example.security.auth.model.response.JwtResponse;
import com.example.security.auth.repository.OtpRepository;
import com.example.security.auth.repository.RoleRepository;
import com.example.security.auth.service.AuthService;
import com.example.security.auth.service.RefreshTokenService;
import com.example.security.auth.util.JwtUtils;
import com.example.security.common.constant.SystemConstant;
import com.example.security.common.exception.AuthException;
import com.example.security.common.exception.ResourceException;
import com.example.security.common.utils.CommonUtils;
import com.example.security.notification.model.message.EmailMessage;
import com.example.security.notification.service.MessageProducer;
import com.example.security.user.model.entity.Profile;
import com.example.security.user.model.entity.User;
import com.example.security.user.model.mapper.ProfileMapper;
import com.example.security.user.model.mapper.UserMapper;
import com.example.security.user.model.response.ProfileDTO;
import com.example.security.user.repository.ProfileRepository;
import com.example.security.user.repository.UserRepository;
import com.example.security.user.service.impl.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final RefreshTokenService refreshTokenService;
    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;

    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateAccessToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authentication);
        return new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    @Transactional
    public void cleanUpExpiredOtpCodes() {
        try{
            List<Otp> byExpiresAtGreaterThan = otpRepository.findByExpiresAtLessThan(LocalDateTime.now());
            otpRepository.deleteAll(byExpiresAtGreaterThan);
            log.error("Expired OTP codes cleaned up successfully. Count: {}", byExpiresAtGreaterThan.size());
        }catch (Exception e){
            log.error("Error cleaning up expired OTP codes: {}", e.getMessage());
        }
    }


    @Override
    public void checkEmailAndUsernameExist(SignupRequest signUpRequest) throws NoSuchFieldException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResourceException(SystemConstant.USER_EXIST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResourceException(SystemConstant.EMAIL_USED);
        }
    }

    @Override
    protected User saveUserProfile(SignupRequest request) {
        Role role = roleRepository.findByName(RoleEnum.valueOf(request.getRole())).orElseThrow(() ->
                new ResourceException(SystemConstant.ROLE_NOT_FOUND));

        User user = userRepository.save(UserMapper.toEntity(request, role));
        Profile profileSaved = profileRepository.save(ProfileMapper.toEntity(request, user));
        user.setProfile(profileSaved);
        return user;
    }

    @Override
    protected String generateAndSaveOtpCode(User user) {
        String otpCode = CommonUtils.randomOtp();
        String otpHash = passwordEncoder.encode(otpCode);

        otpRepository.save(
                Otp.builder()
                        .otpHash(otpHash)
                        .user(user)
                        .type(OtpTypeEnum.SIGNUP)
                        .build()
        );
        return otpCode;
    }

    @Override
    protected void notifyUserSignup(SignupRequest request, String otpCode) throws JsonProcessingException {
        EmailMessage message = EmailMessage.builder()
                .to(request.getEmail())
                .subject("Verify your account")
                .templateKey("verify-otp")
                .paramMap(new HashMap<>() {{
                    put("otp_code", otpCode);
                }})
                .build();

        messageProducer.sendEmail(objectMapper.writeValueAsString(message));
        messageProducer.sendSMS("Demo SMS message");
    }

    @Override
    protected Otp getAndValidate(VerifyOtpRequest request) {
        Otp otp = otpRepository.findByUser_EmailAndType(request.getEmail(), OtpTypeEnum.SIGNUP)
                .orElseThrow(() -> new AuthException("validate", "Otp not found"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AuthException("validate", "Otp expired", null);
        }

        if (otp.getAttemptsCount() >= otp.getMaxAttempts()) {
            throw new AuthException("validate", "Otp max attempts exceeded", null);
        }

        if (otp.getOtpHash().equals(passwordEncoder.encode(request.getOtpCode()))) {
            throw new AuthException("validate", "Otp invalid", null);
        }

        return otp;
    }

    @Override
    protected void acceptUserOtp(Otp otp) {
        User user = userRepository.findById(otp.getUser().getId()).orElseThrow(() -> new ResourceException(SystemConstant.USER_NOT_FOUND));
        user.setVerify(true);
        userRepository.save(user);
    }

    @Override
    protected void updateAttemptCount(Otp otp) {
        if (Objects.nonNull(otp)){
            otp.setAttemptsCount(otp.getAttemptsCount() + 1);
            otpRepository.save(otp);
        }
    }

    public ProfileDTO getProfile(String username) throws NoSuchFieldException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceException(SystemConstant.USER_NOT_FOUND));
        Profile profile = profileRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceException(SystemConstant.PROFILE_NOT_FOUND));
        return ProfileMapper.toDTO(profile, user);
    }
}
