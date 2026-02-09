package com.example.security.question.service;

import com.example.security.question.model.entity.Question;
import com.example.security.question.model.request.QuestionAcceptRequest;
import com.example.security.question.model.request.QuestionReplyRequest;
import com.example.security.question.model.request.QuestionRequest;
import com.example.security.question.model.request.QuestionUpdateRequest;
import com.example.security.question.model.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
    List<QuestionResponse> getAllQuestions();
    QuestionResponse getQuestionById(Long id);
    QuestionResponse createQuestion(QuestionRequest request);
    QuestionResponse updateQuestion(Long id, QuestionUpdateRequest request);
    void deleteQuestion(Long id);
    QuestionResponse replyToQuestion(Long id, QuestionReplyRequest request);
    QuestionResponse acceptQuestion(Long id, QuestionAcceptRequest request);
}
