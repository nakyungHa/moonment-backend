package com.moonment.controller;

import com.moonment.dto.question.QuestionResponse;
import com.moonment.entity.Question;
import com.moonment.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
