package com.moonment.repository;

import com.moonment.entity.Streak;
import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreakRepository extends JpaRepository<Streak, Integer> {

    Optional<Streak> findByUser(User user);
}
