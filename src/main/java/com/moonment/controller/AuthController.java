package com.moonment.controller;

import com.moonment.dto.user.GoogleLoginRequest;
import com.moonment.dto.user.LoginResponse;
import com.moonment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google")
    public LoginResponse googleLogin(
            @RequestBody GoogleLoginRequest request
    ) {
       return authService.googleLogin(request);
    }

}
