package com.moonment.repository;

import com.moonment.entity.Answer;
import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByUserAndRecordDate(User user, LocalDate recordDate);
    boolean existsByUserAndRecordDate(User user, LocalDate recordDate);

    @Query("""
            SELECT new com.moonment.repository.AnswerRepository$AnswerProjection(
                a.recordDate, a.question.questionId, a.question.questionType.type, a.content, a.isSkipped)
            FROM Answer a
            WHERE a.user = :user AND a.recordDate BETWEEN :weekStart AND :weekEnd
            ORDER BY a.recordDate ASC, a.question.questionId ASC
            """)
    List<AnswerProjection> findWeeklyAnswers(@Param("user") User user,
                                              @Param("weekStart") LocalDate weekStart,
                                              @Param("weekEnd") LocalDate weekEnd);

    record AnswerProjection(
            LocalDate recordDate,
            Integer questionId,
            String questionType,
            String content,
            boolean skipped
    ) {}
}
