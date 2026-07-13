package com.moonment.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class HomeResponse {

    private String nickname;

    private Boolean answeredToday;

    private Integer streakCount;

    private Integer remainingDays;

    private LocalDate oneWeekAgo;
}
