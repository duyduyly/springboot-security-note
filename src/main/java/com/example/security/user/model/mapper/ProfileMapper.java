package com.example.security.user.model.mapper;

import com.example.security.user.model.response.ProfileDTO;
import com.example.security.user.model.entity.Profile;
import com.example.security.user.model.entity.User;
import com.example.security.auth.model.request.SignupRequest;

public class ProfileMapper {

    public static ProfileDTO toDTO(Profile profile, User user) {
       return ProfileDTO.builder()
                .id(profile.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .address(profile.getAddress())
                .lastName(profile.getFirstName())
                .firstName(profile.getLastName())
                .old(profile.getOld())
                .build();
    }

    public static Profile mapWithSignupRequest(SignupRequest signUpRequest, User user) {
        return Profile.builder()
                .user(user)
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .build();
    }
}
