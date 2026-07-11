package com.moonment.entity;

import com.moonment.enums.Goal;
import com.moonment.enums.PreferredTime;
import com.moonment.enums.Role;
import com.moonment.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name="user_id")
    private UUID userId;

    //로그인 구조 변경 -> id, pw 사용
    private String loginId;

    private String password;

    private String email;

    //private String socialId;

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

    public void updateOnboarding(
            String name,
            Sex sex,
            Goal goal,
            PreferredTime preferredTime,
            Boolean alertEnabled,
            Boolean aiAnalysisEnabled
    ) {
        this.name = name;
        this.sex = sex;
        this.goal = goal;
        this.preferredTime = preferredTime;
        this.alertEnabled = alertEnabled;
        this.aiAnalysisEnabled = aiAnalysisEnabled;
    }
}
