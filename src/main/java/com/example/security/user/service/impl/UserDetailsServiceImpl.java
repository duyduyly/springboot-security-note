package com.example.security.user.service.impl;

import com.example.security.auth.model.entity.Permission;
import com.example.security.auth.model.enums.RoleEnum;
import com.example.security.auth.service.RoleService;
import com.example.security.common.exception.ResourceException;
import com.example.security.user.model.entity.User;
import com.example.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * interface has a method to load
 * User by username and returns a UserDetails object that Spring Security can use for authentication and validation
 *
 * @return load user and Return userDetail to authentication and validation
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsImpl userDetails;
    private final RoleService roleService;

    public static final String USER_NOT_FOUND = "Username not found ";

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndActiveIsAndVerifyIs(username, true, true)
                .orElseThrow(() -> new ResourceException(USER_NOT_FOUND));

        RoleEnum roleEnum = user.getRole().getName();
        List<Permission> permissionList = roleService.getPermissionList(roleEnum);
        List<String> permissionStrList = permissionList.stream().map(Permission::getName).toList();
        return userDetails.build(user, permissionStrList);
    }



}
