package com.moonment.dto;

import com.moonment.enums.Goal;
import com.moonment.enums.PreferredTime;
import com.moonment.enums.Sex;
import lombok.Getter;

@Getter
public class OnboardingRequest {

    private String name;

    private Sex sex;

    private Goal goal;

    private PreferredTime preferredTime;

    private Boolean alertEnabled;

    private Boolean aiAnalysisEnabled;
}