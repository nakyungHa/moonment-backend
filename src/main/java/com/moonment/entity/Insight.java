package com.moonment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Insight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer insightId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate weekStart;

    private Integer recordCount;

    @Column(columnDefinition = "jsonb")
    private String aiResult;
}
