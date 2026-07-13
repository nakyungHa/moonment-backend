package com.moonment.controller;

import com.moonment.dto.home.HomeResponse;
import com.moonment.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    public HomeResponse home(
            @RequestParam UUID userId
    ) {
        return homeService.getHome(userId);
    }

}
