package com.moonment.repository;

import com.moonment.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermsRepository extends JpaRepository<Terms, Integer> {

    Optional<Terms> findByVersion(String version);

}
