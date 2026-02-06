package com.example.security.question.model.mapper;

import com.example.security.question.model.entity.Question;
import com.example.security.question.model.enums.QuestionStatusEnum;
import com.example.security.question.model.request.QuestionRequest;
import com.example.security.question.model.request.QuestionUpdateRequest;
import com.example.security.question.model.response.QuestionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class QuestionMapper {

    public QuestionResponse toResponse(Question question){
        return QuestionResponse.builder()
                .topic(question.getTopic())
                .detail(question.getDetail())
                .status(question.getStatus().name())
                .answer(question.getAnswer())
                .createdAt(this.safeToString(question.getCreatedAt()))
                .createdBy(this.safeToString(question.getCreatedBy()))
                .updatedAt(this.safeToString(question.getUpdatedAt()))
                .updatedBy(this.safeToString(question.getUpdatedBy()))
                .build();
    }


    public List<QuestionResponse> toResponseList(List<Question> questions){
        return questions.stream()
                .map(this::toResponse)
                .toList();
    }

    public Question toEntity(QuestionRequest request){
        return Question.builder()
                .topic(request.getTopic())
                .detail(request.getDetail())
                .status(this.safeToEnum(request.getStatus()))
                .build();
    }

    public Question toEntity(QuestionUpdateRequest request, Question existingQuestion){
        existingQuestion.setTopic(request.getTopic());
        existingQuestion.setDetail(request.getDetail());
        return existingQuestion;
    }

    private String safeToString(Object obj) {
        return Objects.nonNull(obj) ? obj.toString() : null;
    }

    private QuestionStatusEnum safeToEnum(String status) {
        return Objects.nonNull(status) ? QuestionStatusEnum.valueOf(status) : null;
    }
}

