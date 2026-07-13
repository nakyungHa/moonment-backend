package com.moonment.repository;

import com.moonment.entity.Insight;
import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface InsightRepository extends JpaRepository<Insight, Integer> {

    boolean existsByUserAndWeekStart(User user, LocalDate weekStart);

    Optional<Insight> findByUserAndWeekStart(User user, LocalDate weekStart);
}
