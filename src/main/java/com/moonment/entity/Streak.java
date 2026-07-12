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
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer streakId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer streakCount;

    private LocalDate lastRecordDate;

    // 연속 기록 +1
    public void increase(LocalDate today) {
        this.streakCount++;
        this.lastRecordDate = today;
    }

    // 스트릭 초기화
    public void reset() {
        this.streakCount = 0;
    }
}
