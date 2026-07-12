package com.moonment.service;

import com.moonment.entity.Question;
import com.moonment.entity.QuestionType;
import com.moonment.repository.QuestionRepository;
import com.moonment.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionTypeRepository questionTypeRepository;

    //id로 질문 조회하는 메서드
    public Question getQuestion(Integer questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Question not found")
        );
    }

    //기록 시작했을 때 질문 세개를 가져오는 메서드(today 두개, self 한개)
    public List<Question> getTodayQuestions() {
        QuestionType todayType = questionTypeRepository.findByType("TODAY")
                .orElseThrow(() -> new IllegalArgumentException("TODAY type not found"));
        QuestionType selfType = questionTypeRepository.findByType("SELF")
                .orElseThrow(() -> new IllegalArgumentException("SELF type not found"));

        //today 질문들을 모아놓은 todayQuestions 리스트 생성하고 순서 섞기(랜덤 추출)
        List<Question> todayQuestions = questionRepository.findByQuestionType(todayType);
        Collections.shuffle(todayQuestions);

        //result: 오늘 기록 시 보여질 3개의 질문을 저장할 리스트
        List<Question> result = new ArrayList<>();
        //오늘의 질문 2개 저장
        result.add(todayQuestions.get(0));
        result.add(todayQuestions.get(1));

        //self 질문들을 모아놓은 selfQuestions 리스트 생성하고 순서 섞기(랜덤 추출)
        List<Question> selfQuestions = questionRepository.findByQuestionType(selfType);
        Collections.shuffle(selfQuestions);

        //self 질문 1개 저장
        result.add(selfQuestions.get(0));

        return result;
    }
}
