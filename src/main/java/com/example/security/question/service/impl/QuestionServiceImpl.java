package com.example.security.question.service.impl;

import com.example.security.question.exception.QuestionException;
import com.example.security.question.model.entity.Question;
import com.example.security.question.model.enums.QuestionStatusEnum;
import com.example.security.question.model.mapper.QuestionMapper;
import com.example.security.question.model.request.QuestionAcceptRequest;
import com.example.security.question.model.request.QuestionReplyRequest;
import com.example.security.question.model.request.QuestionRequest;
import com.example.security.question.model.request.QuestionUpdateRequest;
import com.example.security.question.model.response.QuestionResponse;
import com.example.security.question.repository.QuestionRepository;
import com.example.security.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper mapper;

    @Override
    public List<QuestionResponse> getAllQuestions() {
        String sortBy = "updatedAt";
        Sort sort = Sort.by(Sort.Order.desc(sortBy));
        List<Question> questions = questionRepository.findAll(sort);
        return mapper.toResponseList(questions);
    }

    @Override
    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElse(new Question());
        return mapper.toResponse(question);
    }

    @Override
    public QuestionResponse createQuestion(QuestionRequest request) {
        Question entity = mapper.toEntity(request);
        Question save = questionRepository.save(entity);
        return mapper.toResponse(save);
    }

    @Override
    public QuestionResponse updateQuestion(Long id, QuestionUpdateRequest request) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionException("", "Question not found"));
        Question entity = mapper.toEntity(request, question);
        Question save = questionRepository.save(entity);
        return mapper.toResponse(save);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public QuestionResponse replyToQuestion(Long id, QuestionReplyRequest request) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionException("", "Question not found"));
        if (!Objects.equals(question.getStatus(),QuestionStatusEnum.ACCEPTED)) throw new QuestionException("", "Question is not Answered");

        question.setAnswer(request.getAnswer());
        question.setStatus(QuestionStatusEnum.REPLIED);
        Question save = questionRepository.save(question);
        return mapper.toResponse(save);
    }

    @Override
    public QuestionResponse acceptQuestion(Long id, QuestionAcceptRequest request) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionException("", "Question not found"));
        if (!Objects.equals(question.getStatus(),QuestionStatusEnum.NEW)) throw new QuestionException("", "Question is not accepted");

        question.setStatus(request.isAccept() ? QuestionStatusEnum.ACCEPTED : QuestionStatusEnum.DECLINED);
        Question save = questionRepository.save(question);
        return mapper.toResponse(save);
    }


}
