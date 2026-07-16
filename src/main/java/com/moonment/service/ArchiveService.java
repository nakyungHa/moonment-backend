package com.moonment.service;

import com.moonment.dto.archive.ArchiveCalendarDayResponse;
import com.moonment.dto.archive.ArchiveCalendarResponse;
import com.moonment.dto.archive.ArchiveDayStatusResponse;
import com.moonment.entity.Answer;
import com.moonment.entity.StreakHistory;
import com.moonment.entity.User;
import com.moonment.repository.AnswerRepository;
import com.moonment.repository.StreakHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArchiveService {

    private final AnswerRepository answerRepository;
    private final StreakHistoryRepository streakHistoryRepository;

    public ArchiveCalendarResponse getCalendar(
            User user,
            Integer year,
            Integer month
    ) {
        // 받아온 year와 month 가지고 YearMonth 타입의 변수 저장
        // YearMonth 클래스에서 그 해의 그 달의 첫날(항상 1일)과
        // 마지막날 (28, 29, 30, 31 등)이 정보를 받아올 수 있음
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 기록을 하면 하루에 9개 정도의 질문에 답변을 하기 때문에
        // recordDate이 동일한 9개의 Answer 객체가 만들어져있다.
        // 첫날과 마지막날의 사이 모든 Answer 객체를 리스트 형태로 저장하고,
        // Answer 객체 하나씩 recordDate를 꺼내서
        // RecordDate를 key로, boolean 값을 value로 하는
        // HashMap을 만들어 거기에 날짜별 그 날의 일기 유무를 저장한다.
        List<Answer> answers =
                answerRepository.findByUserAndRecordDateBetween(
                        user, startDate, endDate
                );
        Map<LocalDate, Boolean> recordMap = new HashMap<>();

        for (Answer answer : answers) {
            recordMap.put(answer.getRecordDate(), true);
        }

        List<StreakHistory> histories =
                streakHistoryRepository
                        .findByUserAndActivityDateBetweenOrderByActivityDateAsc(
                                user,
                                startDate, endDate
                        );
        Map<LocalDate, Integer> moonMap = new HashMap<>();

        for (StreakHistory history : histories) {
            int moonLevel = Math.min(history.getStreakCount(), 15);
            moonMap.put(history.getActivityDate(), moonLevel);
        }

        // 이제 ArchiveCalendarDayResponse 에 저장하기 위해
        // user, hasRecord, moonLevel 을 저장하자
        List<ArchiveCalendarDayResponse> days = new ArrayList<>();

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);

            // key 값(date)로 value값(오직 true인 경우만 저장해놓음)이 있으면 get, 없으면 false로 저장
            boolean hasRecord =
                    recordMap.getOrDefault(date, false);
            int moonLevel =
                    moonMap.getOrDefault(date, 0);
            days.add(new ArchiveCalendarDayResponse(
                    date, hasRecord, moonLevel)
            );
        }

        return new ArchiveCalendarResponse(year, month, days);
    }

    public ArchiveDayStatusResponse getDayStatus(User user, LocalDate date) {
        boolean hasRecord = answerRepository.existsByUserAndRecordDate(user, date);

        LocalDate weekStart = date.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        );

        LocalDate weekEnd = weekStart.plusDays(6);

        // 한 주에 총 몇번 기록 했는지 계산하여 변수 저장
        long weekRecordCount =
                answerRepository.countDistinctRecordDatesByUserAndWeek(
                        user, weekStart, weekEnd
                );

        // 해당 주가 끝났는지
        boolean isCompleteWeek = weekEnd.isBefore(LocalDate.now());

        // 인사이트를 볼 수 있는지: 해당 주가 끝나야 하고 && 기록이 3번 이상
        boolean canViewInsight = isCompleteWeek && weekRecordCount >= 3;

        return new ArchiveDayStatusResponse(
                date,
                hasRecord,
                canViewInsight,
                weekStart
        );
    }
}
