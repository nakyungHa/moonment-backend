package com.moonment.repository;

import com.moonment.entity.StreakHistory;
import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StreakHistoryRepository extends JpaRepository<StreakHistory, Integer> {
    Optional<StreakHistory> findByUserAndActivityDate(User user, LocalDate activityDate
    );

    List<StreakHistory> findByUserAndActivityDateBetweenOrderByActivityDateAsc(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );
}
