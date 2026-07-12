package com.moonment.controller;

import com.moonment.dto.answer.AnswerListResponse;
import com.moonment.dto.answer.AnswerResponse;
import com.moonment.dto.answer.SaveAnswerRequest;
import com.moonment.entity.Answer;
import com.moonment.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    public ResponseEntity<Void> saveAnswers(@RequestBody SaveAnswerRequest request) {
        answerService.saveAnswers(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{recordDate}")
    public AnswerListResponse getAnswers(@PathVariable LocalDate recordDate,
                                         @RequestParam UUID userId) {
        //답변이 존재하는지 확인하는 bool 속성 추가 위해 답변서비스에서 답변들 받아와서 Answer 엔티티 객체인 answers에 저장
        //원래는 답변 서비스에서 getAnswers 로 받아와서 해당 Answer 객체들을 stream, map, toList를 통해 dto로 변환 후
        //AnswerResponse의 List 형태로 responses라는 이름으로 저장했었는데,
        //이를 answers, responses 에 나눠서 저장하고 Answer 엔티티 객체의 isEmpty() 메서드를 사용

        List<Answer> answers = answerService.getAnswers(userId, recordDate);
        List<AnswerResponse> responses = answers
                .stream()
                .map(answer -> new AnswerResponse(
                        answer.getQuestion().getQuestionId(),
                        answer.getQuestion().getQuestionType().getType(),
                        answer.getQuestion().getContent(),
                        answer.getContent()
                ))
                .toList();
        return new AnswerListResponse(recordDate,
                !answers.isEmpty(),
                responses);
    }
}

