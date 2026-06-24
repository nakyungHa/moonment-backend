package com.moonment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name="user_id")
    private UUID userId;

    private String email;

    private String socialId;

    private String name;

    private Boolean alertEnabled;

    private Boolean aiAnalysisEnabled;

    private LocalDateTime createdAt;
}
