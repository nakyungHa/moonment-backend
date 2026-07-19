package com.moonment.controller;

import com.moonment.dto.home.HomeResponse;
import com.moonment.entity.User;
import com.moonment.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public HomeResponse home(
            @AuthenticationPrincipal User user
    ) {
        return homeService.getHome(user.getUserId());
    }

}
