package com.moonment.service;

import com.moonment.entity.Streak;
import com.moonment.entity.User;
import com.moonment.repository.StreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final StreakRepository streakRepository;

    // 기록 저장 시 updateStreak 실행
    public void updateStreak (User user) {
        // 오늘 날짜 받아오기
        LocalDate today = LocalDate.now();
        // user의 streak 정보 받아오기
        Streak streak = streakRepository.findByUser(user)
                .orElse(null);

        // streak 정보가 없을 때, user 정보와 스트릭 일수 첫 설정
        if (streak == null) {
            streakRepository.save(
                    Streak.builder()
                            .user(user)
                            .streakCount(1)
                            .lastRecordDate(today)
                            .build()
            );
             return;
        }
        // 이미 오늘의 streak이 올라 갔을 때 -> streak ++ 막기
        if (streak.getLastRecordDate().equals(today)) {
            return;
        }
        // streak 업데이트
        streak.increase(today);
        streakRepository.save(streak);
    }

    // 기록 없을 시 스트릭 정보 reset <- 스케줄러 사용해서 모든 사용자의 streak 훑기
    // yesterday 값을 저장하고, lastRecordDate /= yesterday인 사용자 스트릭 0으로 초기화
    // 00:00 시에 저장한 사용자의 스트릭 -> 0으로 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void resetExpiredStreaks() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        for (Streak streak : streakRepository.findAll()) {
            if (streak.getLastRecordDate() == null) {
                continue;
            }
            if (!streak.getLastRecordDate().equals(yesterday)) {
                streak.reset();
                streakRepository.save(streak);
            }
        }
    }
}
