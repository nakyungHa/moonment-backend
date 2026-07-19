package com.moonment.repository;

import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    // 인사이트 주간 스케줄러가 AI 분석에 동의한 유저만 대상으로 돌리기 위해 사용.
    List<User> findByAiAnalysisEnabledTrue();
}
