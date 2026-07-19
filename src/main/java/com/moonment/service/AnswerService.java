package com.moonment.service;

import com.moonment.dto.answer.AnswerItemRequest;
import com.moonment.dto.answer.SaveAnswerRequest;
import com.moonment.entity.Answer;
import com.moonment.entity.Question;
import com.moonment.entity.User;
import com.moonment.repository.AnswerRepository;
import com.moonment.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final StreakService streakService;

    public void saveAnswers(User user, SaveAnswerRequest request) {

        //답변들을 한번에 저장할 리스트 answers 만들기
        List<Answer> answers = new ArrayList<>();

        //전체 답변 요청인 SaveAnswerRequest의 request에 AnswerItemRequest 리스트가 있음
        //그걸 getAnswers() 메서드로 꺼내와서 하나씩 item에 저장
        //item 하나씩 꺼내서 정보를 db에 저장
        for (AnswerItemRequest item : request.getAnswers()) {
            //item에서 question 객체 받아오기
            Question question = questionRepository.findById(item.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));
            //Answer 객체에 저장
            Answer answer = Answer.builder()
                    .question(question)
                    .user(user)
                    .recordDate(request.getRecordDate())
                    .answeredAt(LocalDateTime.now())
                    .content(item.getContent())
                    .isSkipped(false)
                    .build();
            answers.add(answer);
        }
        answerRepository.saveAll(answers);
        streakService.updateStreak(user);
    }

    public List<Answer> getAnswers(User user, LocalDate recordDate) {
        return answerRepository.findByUserAndRecordDate(user, recordDate);
    }

}
