package com.moonment.dto.auth;

import com.moonment.enums.Goal;
import com.moonment.enums.Sex;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class SignupRequest {

    private String loginId;
    private String password;
    private String passwordConfirm;

    private String name;
    private String email;

    private Sex sex;
    private LocalTime recordTime;
    private Goal goal;

    private Boolean serviceTermsAgreed;
    private Boolean privacyTermsAgreed;
    private Boolean aiAnalysisTermsAgreed;
}
