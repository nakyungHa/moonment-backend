package com.moonment.repository;

import com.moonment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);
}
