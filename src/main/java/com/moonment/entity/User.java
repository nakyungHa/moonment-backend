package com.moonment.entity;

import com.moonment.enums.Goal;
import com.moonment.enums.PreferredTime;
import com.moonment.enums.Role;
import com.moonment.enums.Sex;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "sex_type")
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "goal_type")
    private Goal goal;

    private Boolean alertEnabled;

    private Boolean aiAnalysisEnabled;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "preferred_time_type")
    private PreferredTime preferredTime;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "role_type")
    private Role role;

    private LocalDateTime createdAt;

    @Builder
    public User(UUID userId,
                String email,
                String socialId,
                String name,
                Sex sex,
                Goal goal,
                Boolean alertEnabled,
                Boolean aiAnalysisEnabled,
                PreferredTime preferredTime,
                Role role,
                LocalDateTime createdAt) {

        this.userId = userId;
        this.email = email;
        this.socialId = socialId;
        this.name = name;
        this.sex = sex;
        this.goal = goal;
        this.alertEnabled = alertEnabled;
        this.aiAnalysisEnabled = aiAnalysisEnabled;
        this.preferredTime = preferredTime;
        this.role = role;
        this.createdAt = createdAt;
    }
}
