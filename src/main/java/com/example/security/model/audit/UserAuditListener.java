package com.example.security.model.audit;


import com.example.security.model.entity.User;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAuditListener {

    @Autowired
   private PasswordEncoder passwordEncoder;

    @PostPersist
    public void PostPersist(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
