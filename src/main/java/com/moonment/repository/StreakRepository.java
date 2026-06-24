package com.moonment.repository;

import com.moonment.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreakRepository extends JpaRepository<Streak, Integer> {
}
