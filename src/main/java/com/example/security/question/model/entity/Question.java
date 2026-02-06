package com.example.security.question.model.entity;

import com.example.security.question.audit.QuestionAudit;
import com.example.security.question.model.enums.QuestionStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@EntityListeners(QuestionAudit.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String topic;

    @Lob
    private String detail;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private QuestionStatusEnum status;

    @Lob
    @Column(name = "answer")
    private String answer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
