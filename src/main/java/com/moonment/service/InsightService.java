package com.moonment.service;

import tools.jackson.databind.json.JsonMapper;
import com.moonment.dto.insight.InsightDto.AnswerPayload;
import com.moonment.dto.insight.InsightDto.DailyEntry;
import com.moonment.dto.insight.InsightDto.WeeklyReportRequest;
import com.moonment.dto.insight.InsightDto.WeeklyReportResponse;
import com.moonment.entity.Insight;
import com.moonment.entity.User;
import com.moonment.repository.AnswerRepository;
import com.moonment.repository.AnswerRepository.AnswerProjection;
import com.moonment.repository.InsightRepository;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsightService {

    // 조건을 바꾸려면 이 숫자만 수정하면 된다.
    private static final int MIN_RECORDS_FOR_INSIGHT = 3;

    private static final String WEEKLY_REPORT_PATH = "/pipeline/weekly-report";

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final InsightRepository insightRepository;
    private final RestTemplate restTemplate;
    private final JsonMapper jsonMapper;

    @Value("${fastapi.base-url}")
    private String fastApiBaseUrl;

    @Scheduled(cron = "0 0 0 * * MON")
    public void generateWeeklyReports() {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(7);
        LocalDate weekEnd = today.minusDays(1);

        log.info("Weekly insight generation started. weekStart={}, weekEnd={}", weekStart, weekEnd);

        List<User> targetUsers = userRepository.findByAiAnalysisEnabledTrue();
        int generatedCount = 0;
        int skippedCount = 0;
        int failedCount = 0;

        for (User user : targetUsers) {
            try {
                if (generateForUser(user, weekStart, weekEnd)) {
                    generatedCount++;
                } else {
                    skippedCount++;
                }
            } catch (Exception e) {
                failedCount++;
                log.error("Weekly insight generation failed. userId={}, weekStart={}", user.getUserId(), weekStart, e);
            }
        }

        log.info("Weekly insight generation completed. total={}, generated={}, skipped={}, failed={}",
                targetUsers.size(), generatedCount, skippedCount, failedCount);
    }

    private boolean generateForUser(User user, LocalDate weekStart, LocalDate weekEnd) {
        if (insightRepository.existsByUserAndWeekStart(user, weekStart)) {
            log.info("Insight already exists, skipping. userId={}, weekStart={}", user.getUserId(), weekStart);
            return false;
        }

        List<AnswerProjection> answers = answerRepository.findWeeklyAnswers(user, weekStart, weekEnd);
        int recordCount = countRecordedDays(answers);

        if (recordCount < MIN_RECORDS_FOR_INSIGHT) {
            log.info("Not enough records, skipping. userId={}, weekStart={}, recordCount={}", user.getUserId(), weekStart, recordCount);
            return false;
        }

        WeeklyReportRequest request = buildRequest(user, weekStart, recordCount, answers);
        WeeklyReportResponse response = callFastApi(request);

        if (response == null || !response.isCompleted()) {
            log.warn("FastAPI did not return a completed report, skipping save. userId={}, weekStart={}, status={}, errorCode={}",
                    user.getUserId(), weekStart, response == null ? null : response.status(), response == null ? null : response.errorCode());
            return false;
        }

        Insight insight = Insight.builder()
                .user(user)
                .weekStart(weekStart)
                .recordCount(recordCount)
                .aiResult(toJson(response))
                .build();
        insightRepository.save(insight);

        log.info("Insight saved. userId={}, weekStart={}, recordCount={}", user.getUserId(), weekStart, recordCount);
        return true;
    }

    // 이 값이 "기록한 날 수"의 유일한 기준(single source of truth)이다.
    // moonment-ai에는 이 값을 recordCount로 그대로 넘겨주고, Python 쪽에서 별도로 재계산하지 않는다(B3).
    private int countRecordedDays(List<AnswerProjection> answers) {
        return (int) answers.stream()
                .filter(a -> !a.skipped())
                .map(AnswerProjection::recordDate)
                .distinct()
                .count();
    }

    private WeeklyReportRequest buildRequest(User user, LocalDate weekStart, int recordCount, List<AnswerProjection> answers) {
        Map<LocalDate, List<AnswerPayload>> byDate = answers.stream()
                .collect(Collectors.groupingBy(
                        AnswerProjection::recordDate,
                        LinkedHashMap::new,
                        Collectors.mapping(a -> new AnswerPayload(
                                a.questionId(), a.questionType(), a.content(), null, a.skipped()
                        ), Collectors.toList())
                ));

        List<DailyEntry> dailyEntries = byDate.entrySet().stream()
                .map(e -> new DailyEntry(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(DailyEntry::recordDate))
                .toList();

        String userContext = user.getGoal() != null ? user.getGoal().getValue() : null;
        return new WeeklyReportRequest(user.getUserId(), user.getName(), weekStart, userContext, recordCount, dailyEntries);
    }

    private WeeklyReportResponse callFastApi(WeeklyReportRequest request) {
        String url = fastApiBaseUrl + WEEKLY_REPORT_PATH;
        try {
            return restTemplate.postForObject(url, request, WeeklyReportResponse.class);
        } catch (RestClientException e) {
            log.error("FastAPI call failed. url={}", url, e);
            return null;
        }
    }

    private String toJson(WeeklyReportResponse response) {
        try {
            return jsonMapper.writeValueAsString(response);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize FastAPI response", e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<String> getWeeklyInsightRaw(UUID userId, LocalDate weekStart) {
        return userRepository.findById(userId)
                .flatMap(user -> insightRepository.findByUserAndWeekStart(user, weekStart))
                .map(Insight::getAiResult);
    }
}
