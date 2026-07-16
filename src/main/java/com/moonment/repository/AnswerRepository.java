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
}
