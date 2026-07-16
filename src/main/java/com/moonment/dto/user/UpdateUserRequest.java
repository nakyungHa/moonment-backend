package com.moonment.dto.user;

import com.moonment.enums.Goal;
import com.moonment.enums.Sex;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class UpdateUserRequest {
    //null이 아닌 필드만 반영됨
    private String name;

    private Sex sex;

    private Goal goal;

    private LocalTime recordTime;

    private Boolean alertEnabled;

    private Boolean aiAnalysisEnabled;
}
