package com.example.security.user.controller;


import com.example.security.auth.service.impl.AuthServiceImpl;
import com.example.security.common.constant.SystemConstant;
import com.example.security.common.model.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthServiceImpl userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MENTOR','REVIEWER')")
    public ResponseEntity<?> getProfile(Principal principal) throws NoSuchFieldException {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(userService.getProfile(principal.getName()))
                .build(), HttpStatus.OK);
    }
}
