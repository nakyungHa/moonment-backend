package com.moonment.controller;

import com.moonment.dto.answer.SaveAnswerRequest;
import com.moonment.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    public void saveAnswers(@RequestBody SaveAnswerRequest request) {
        answerService.saveAnswers(request);
    }
}
