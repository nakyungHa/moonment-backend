package com.moonment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer streakId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer streakCount;

    private LocalDate lastRecordDate;
}
