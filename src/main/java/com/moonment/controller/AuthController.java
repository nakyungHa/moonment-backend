package com.moonment.controller;

import com.moonment.dto.auth.CheckLoginIdResponse;
import com.moonment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    public CheckLoginIdResponse checkLoginId(@RequestParam String loginId) {
        boolean available = authService.isLoginIdAvailable(loginId);

        return new CheckLoginIdResponse(available);
    }


}
