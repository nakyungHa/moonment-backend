package com.moonment.controller;

import com.moonment.config.JwtProvider;
import com.moonment.dto.home.HomeResponse;import com.moonment.service.HomeService;import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final JwtProvider jwtProvider;

    public HomeResponse home(
            @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        String email = jwtProvider.getEmail(token);
        return homeService.getHome(email);
    }

}
