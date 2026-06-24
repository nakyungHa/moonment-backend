package com.moonment.repository;

import com.moonment.entity.Insight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsightRepository extends JpaRepository<Insight, Integer> {
}
