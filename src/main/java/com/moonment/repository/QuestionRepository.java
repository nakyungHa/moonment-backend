package com.moonment.repository;

import com.moonment.entity.Question;
import com.moonment.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuestionType(QuestionType questionType);
}