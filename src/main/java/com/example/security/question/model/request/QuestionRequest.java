package com.example.security.question.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuestionRequest {

    @NotBlank
    private String topic;

    @NotBlank
    private String detail;

    @NotBlank
    private String status;
}
