package com.example.security.question.controller;

import com.example.security.common.constant.SystemConstant;
import com.example.security.common.model.response.CommonResponse;
import com.example.security.question.model.request.QuestionAcceptRequest;
import com.example.security.question.model.request.QuestionReplyRequest;
import com.example.security.question.model.request.QuestionRequest;
import com.example.security.question.model.request.QuestionUpdateRequest;
import com.example.security.question.model.response.QuestionResponse;
import com.example.security.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','REVIEWER')")
    public ResponseEntity<?> getQuestions() {
        return ResponseEntity.ok(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(questionService.getAllQuestions())
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MENTOR','REVIEWER')")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(questionService.getQuestionById(id))
                .build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid QuestionRequest request) {
        return ResponseEntity.ok(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(questionService.createQuestion(request))
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody @Valid QuestionUpdateRequest request) {
        return ResponseEntity.ok(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(questionService.updateQuestion(id, request))
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(new ArrayList<>())
                .build());
    }

    @PutMapping("/reply/{id}")
    @PreAuthorize("hasAnyRole('MENTOR')")
    public ResponseEntity<?> replyToQuestion(@PathVariable Long id, @RequestBody @Valid QuestionReplyRequest request) {
        QuestionResponse sampleAnswer = questionService.replyToQuestion(id, request);
        return ResponseEntity.ok(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(sampleAnswer)
                .build());
    }

    @PutMapping("/accept/{id}")
    @PreAuthorize("hasAnyRole('REVIEWER','MENTOR')")
    public ResponseEntity<?> acceptAnswer(@PathVariable Long id, @RequestBody @Valid QuestionAcceptRequest request) {
        QuestionResponse sampleAnswer = questionService.acceptQuestion(id, request);
        return ResponseEntity.ok(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(SystemConstant.SUCCESS)
                .data(sampleAnswer)
                .build());
    }

}
