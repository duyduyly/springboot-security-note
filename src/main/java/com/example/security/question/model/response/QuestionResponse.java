package com.example.security.question.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {
    private String topic;
    private String detail;
    private String status;
    private String answer;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
}
