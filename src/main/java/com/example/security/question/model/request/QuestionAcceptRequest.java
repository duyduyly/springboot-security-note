package com.example.security.question.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuestionAcceptRequest {

    @NotNull
    private boolean accept;
}
