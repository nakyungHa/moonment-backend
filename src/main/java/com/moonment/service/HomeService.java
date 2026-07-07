package com.moonment.service;

import com.moonment.dto.home.HomeResponse;
import com.moonment.entity.User;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;

    public HomeResponse getHome(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return new HomeResponse(user.getName());
    }

}
