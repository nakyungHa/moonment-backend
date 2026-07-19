package com.moonment.dto.user;

import com.moonment.enums.Goal;
import com.moonment.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class UserProfileResponse {

    private String name;

    private String email;

    private Sex sex;

    private Goal goal;

    private LocalTime recordTime;

    private Boolean alertEnabled;

    private Boolean aiAnalysisEnabled;
}
