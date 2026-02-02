package com.example.security.auth.service.impl;

import com.example.security.auth.model.enums.RoleEnum;
import com.example.security.auth.service.AuthService;
import com.example.security.common.constant.SystemConstant;
import com.example.security.common.exception.ResourceException;
import com.example.security.user.model.response.ProfileDTO;
import com.example.security.user.model.entity.Profile;
import com.example.security.auth.model.entity.RefreshToken;
import com.example.security.auth.model.entity.Role;
import com.example.security.user.model.entity.User;
import com.example.security.user.model.mapper.ProfileMapper;
import com.example.security.user.model.mapper.UserMapper;
import com.example.security.auth.model.request.LoginRequest;
import com.example.security.auth.model.request.SignupRequest;
import com.example.security.auth.model.response.JwtResponse;
import com.example.security.user.repository.ProfileRepository;
import com.example.security.auth.repository.RoleRepository;
import com.example.security.user.repository.UserRepository;
import com.example.security.user.service.impl.UserDetailsImpl;
import com.example.security.auth.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenServiceImpl;


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

        RefreshToken refreshToken = refreshTokenServiceImpl.createRefreshToken(authentication);
        return new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public void signUp(SignupRequest signUpRequest) throws NoSuchFieldException {
        try{
            this.checkEmailAndUsernameExist(signUpRequest);
            User user = userRepository.save(UserMapper.mapWithSignRequest(signUpRequest, this.selectRole(signUpRequest)));
            profileRepository.save(ProfileMapper.mapWithSignupRequest(signUpRequest, user));
            log.info("User {} SignUp Success!", user.getUsername());
        }catch (Exception e){
            throw e;
        }
    }

    private Set<Role> selectRole(SignupRequest signUpRequest) {
        //get Role
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        /**
         * check role
         if role is null then Set role is  ROLE_USER
         if role ROLE_ADMIN or ROLE_MODERATOR then it will add in Set Role
         * */
        if (Objects.isNull(strRoles) || strRoles.isEmpty()) {
            roles.add(roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new ResourceException(SystemConstant.ROLE_NOT_FOUND)));
            return roles;
        }

        strRoles.forEach(role -> {
            var eRole = switch (role) {
                case "admin" -> RoleEnum.ROLE_ADMIN;
                case "moderator" -> RoleEnum.ROLE_MODERATOR;
                default -> RoleEnum.ROLE_USER;
            };

            Role userRole = roleRepository.findByName(eRole)
                    .orElseThrow(() -> new ResourceException(SystemConstant.ROLE_NOT_FOUND));
            roles.add(userRole);
        });
        return roles;
    }

    private void checkEmailAndUsernameExist(SignupRequest signUpRequest) throws NoSuchFieldException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResourceException(SystemConstant.USER_EXIST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResourceException(SystemConstant.EMAIL_USED);
        }
    }

    public ProfileDTO getProfile(String username) throws NoSuchFieldException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceException(SystemConstant.USER_NOT_FOUND));
        Profile profile = profileRepository.findById(user.getId()).orElseThrow(() -> new ResourceException(SystemConstant.PROFILE_NOT_FOUND));
        return ProfileMapper.toDTO(profile, user);
    }
}
