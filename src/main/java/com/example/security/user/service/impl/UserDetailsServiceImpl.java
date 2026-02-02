package com.example.security.user.service.impl;

import com.example.security.common.exception.ResourceException;
import com.example.security.user.model.entity.User;
import com.example.security.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * interface has a method to load
 * User by username and returns a UserDetails object that Spring Security can use for authentication and validation
 *
 * @return load user and Return userDetail to authentication and validation
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsImpl userDetails;

    public static final String USER_NOT_FOUND = "Username not found ";

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndActiveIsAndOtpVerifyIs(username, true, true)
                .orElseThrow(() -> new ResourceException(USER_NOT_FOUND));

        return userDetails.build(user);
    }

}
