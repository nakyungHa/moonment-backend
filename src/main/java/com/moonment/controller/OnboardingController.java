package com.moonment.controller;

import com.moonment.config.JwtProvider;
import com.moonment.dto.user.OnboardingRequest;
import com.moonment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class OnboardingController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/onboarding")
    public String onboarding(
            @RequestHeader("Authorization") String authorization,
            @RequestBody OnboardingRequest request
    ) {

        String token = authorization.substring(7); // "Bearer " 제거

        String email = jwtProvider.getEmail(token);

        userService.onboarding(email, request);

        return "온보딩 완료";
    }
}