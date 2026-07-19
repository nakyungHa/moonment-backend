package com.moonment.service;

import com.moonment.dto.home.HomeResponse;
import com.moonment.entity.Streak;
import com.moonment.entity.User;
import com.moonment.repository.AnswerRepository;
import com.moonment.repository.StreakRepository;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;
    private final StreakRepository streakRepository;
    private final AnswerRepository answerRepository;

    public HomeResponse getHome(UUID userId) {
        // 유저 정보 받아오기
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
        // 오늘 기록 유무 받아오기
        boolean answeredToday = answerRepository.existsByUserAndRecordDate(user, LocalDate.now());
        // 스트릭 정보 받아오기
        Streak streak = streakRepository.findByUser(user)
                .orElse(null);
        // 스트릭 일자를 받을 streakCount 변수 생성하고 일단 0으로 초기화
        int streakCount = 0;
        // streak 객체가 null이 아닌 경우 streakCount 에 스트릭 일수 대입
        if (streak != null) {
            streakCount = streak.getStreakCount();
        }
        int remainingDays = Math.max(0, 16-streakCount);
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

        return new HomeResponse(
                user.getName(),
                answeredToday,
                streakCount,
                remainingDays,
                oneWeekAgo
        );
    }
}
