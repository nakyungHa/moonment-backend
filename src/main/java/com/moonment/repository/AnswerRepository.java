package com.moonment.repository;

import com.moonment.entity.Answer;
import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByUserAndRecordDate(User user, LocalDate recordDate);
    boolean existsByUserAndRecordDate(User user, LocalDate recordDate);
}
