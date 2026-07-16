package com.moonment.service;

import com.moonment.dto.user.UpdateUserRequest;
import com.moonment.dto.user.UserProfileResponse;
import com.moonment.entity.User;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponse getProfile(User user) {
        return new UserProfileResponse(
                user.getName(),
                user.getEmail(),
                user.getSex(),
                user.getGoal(),
                user.getRecordTime(),
                user.getAlertEnabled(),
                user.getAiAnalysisEnabled()
        );
    }

    @Transactional
    public UserProfileResponse updateProfile(User user, UpdateUserRequest request) {
        user.updateProfile(
                request.getName(),
                request.getSex(),
                request.getGoal(),
                request.getRecordTime(),
                request.getAlertEnabled(),
                request.getAiAnalysisEnabled()
        );
        userRepository.save(user);
        return getProfile(user);
    }
}
