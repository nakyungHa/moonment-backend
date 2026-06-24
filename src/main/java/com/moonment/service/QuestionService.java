package com.moonment.service;

import com.moonment.entity.Question;
import com.moonment.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question getQuestion(Integer questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Question not found")
        );
    }
}
