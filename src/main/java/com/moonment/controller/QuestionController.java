package com.moonment.controller;

import com.moonment.dto.question.QuestionListResponse;
import com.moonment.dto.question.QuestionResponse;
import com.moonment.entity.Question;
import com.moonment.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{id}")
    public QuestionResponse getQuestion(@PathVariable Integer id) {

        Question question = questionService.getQuestion(id);

        return new QuestionResponse(
                question.getQuestionId(),
                question.getQuestionType().getType(),
                question.getContent()
        );
    }

    //오늘 질문 조회 시 매핑
    @GetMapping("/today")
    public QuestionListResponse getTodayQuestions() {
        List<QuestionResponse> responses = questionService.getTodayQuestions()
                .stream()
                .map(question -> new QuestionResponse(
                        question.getQuestionId(),
                        question.getQuestionType().getType(),
                        question.getContent()
                ))
                .toList();
        return new QuestionListResponse(responses);
    }
}
