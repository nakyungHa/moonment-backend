package com.moonment.entity;

import com.moonment.enums.Goal;
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
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
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

    private LocalTime recordTime;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "role_type")
    private Role role;

    private LocalDateTime createdAt;

    public void updateProfile(String name, Sex sex, Goal goal, LocalTime recordTime,
                               Boolean alertEnabled, Boolean aiAnalysisEnabled) {
        if (name != null) this.name = name;
        if (sex != null) this.sex = sex;
        if (goal != null) this.goal = goal;
        if (recordTime != null) this.recordTime = recordTime;
        if (alertEnabled != null) this.alertEnabled = alertEnabled;
        if (aiAnalysisEnabled != null) this.aiAnalysisEnabled = aiAnalysisEnabled;
    }

}
