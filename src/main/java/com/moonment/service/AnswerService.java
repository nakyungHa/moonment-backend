package com.moonment.service;

import com.moonment.dto.answer.AnswerItemRequest;
import com.moonment.dto.answer.SaveAnswerRequest;
import com.moonment.entity.Answer;
import com.moonment.entity.Question;
import com.moonment.entity.User;
import com.moonment.repository.AnswerRepository;
import com.moonment.repository.QuestionRepository;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public void saveAnswers(SaveAnswerRequest request) {

        //user 정보 찾기
        User user = userRepository.findById(
                request.getUserId()
//                UUID.fromString("751f3f69-3077-4de2-af4a-4c406648d927") //하드코딩
        ).orElseThrow(() -> new IllegalArgumentException("User not found"));
        // TODO: JWT 인증 적용 후 request.getUserId() 제거 예정
        // 나중에 jwt 붙이고 나서서는 아래와 같이 수정해서 user 정보 받아와야 함
        //User user = userService.getCurrentUser();

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
    }

    public List<Answer> getAnswers(UUID userId, LocalDate recordDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return answerRepository.findByUserAndRecordDate(user, recordDate);
    }

}
