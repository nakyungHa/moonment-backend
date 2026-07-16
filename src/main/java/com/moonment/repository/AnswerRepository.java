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
    List<Answer> findByUserAndRecordDateBetween (
            User user,
            LocalDate start,
            LocalDate end
    );
    @Query("""
            SELECT COUNT(DISTINCT a.recordDate)
            FROM Answer a
            WHERE a.user = :user
            AND a.recordDate BETWEEN :weekStart AND :weekEnd
        """)
    long countDistinctRecordDatesByUserAndWeek(
            @Param("user") User user,
            @Param("weekStart") LocalDate weekStart,
            @Param("weekEnd") LocalDate weekEnd
    );

    // 인사이트 주간 리포트용 프로젝션 — InsightService.generateForUser에서 사용.
    // Answer 엔티티 대신 필요한 필드만 뽑아서 FastAPI 요청 DTO(AnswerPayload)로 바로 변환한다.
    record AnswerProjection(
            LocalDate recordDate,
            Integer questionId,
            String questionType,
            String content,
            Boolean skipped
    ) {}

    @Query("""
            SELECT new com.moonment.repository.AnswerRepository$AnswerProjection(
                a.recordDate, a.question.questionId, a.question.questionType.type, a.content, a.isSkipped
            )
            FROM Answer a
            WHERE a.user = :user
            AND a.recordDate BETWEEN :weekStart AND :weekEnd
            ORDER BY a.recordDate
        """)
    List<AnswerProjection> findWeeklyAnswers(
            @Param("user") User user,
            @Param("weekStart") LocalDate weekStart,
            @Param("weekEnd") LocalDate weekEnd
    );
}
