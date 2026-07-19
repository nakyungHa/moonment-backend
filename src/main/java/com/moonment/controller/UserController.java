package com.moonment.controller;

import com.moonment.dto.user.UpdateUserRequest;
import com.moonment.dto.user.UserProfileResponse;
import com.moonment.entity.User;
import com.moonment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserProfileResponse getMe(@AuthenticationPrincipal User user) {
        return userService.getProfile(user);
    }

    @PatchMapping("/me")
    public UserProfileResponse updateMe(@AuthenticationPrincipal User user,
                                         @RequestBody UpdateUserRequest request) {
        return userService.updateProfile(user, request);
    }
}
