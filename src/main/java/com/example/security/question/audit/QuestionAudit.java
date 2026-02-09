package com.example.security.question.audit;

import com.example.security.question.model.entity.Question;
import com.example.security.user.util.UserUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class QuestionAudit {

    @PrePersist
    public void beforeCreate(Question entity) {
        entity.setCreatedBy(UserUtils.getUserDetails().getUsername());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void beforeUpdate(Question entity) {
        entity.setUpdatedBy(UserUtils.getUserDetails().getUsername());
        entity.setUpdatedAt(LocalDateTime.now());
    }


}
