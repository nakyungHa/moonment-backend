package com.moonment.service;

//import com.moonment.dto.user.OnboardingRequest;
import com.moonment.entity.User;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    /*

    private final UserRepository userRepository;

    public void onboarding(String email, OnboardingRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.updateOnboarding(
                request.getName(),
                request.getSex(),
                request.getGoal(),
                request.getPreferredTime(),
                request.getAlertEnabled(),
                request.getAiAnalysisEnabled()
        );

        userRepository.save(user);
    }*/
}