package com.moonment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreakHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer streakHistoryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate activityDate;

    private Integer streakCount;
}
