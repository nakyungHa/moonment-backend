package com.moonment.dto.auth;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String loginId;
    private String password;
}
