package com.moonment.repository;

import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByEmail(String email);

    List<User> findByAiAnalysisEnabledTrue(); // AI 분석시 활성화된 사용자 retrieval 위해 추가
}
