package com.moonment.controller;

import com.moonment.dto.insight.InsightDto.NoReportResponse;
import com.moonment.entity.User;
import com.moonment.service.InsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;

// 조회 전용 API. InsightService가 매주 월요일 스케줄러로 미리 만들어둔 리포트를 그대로 내려줌.
// ArchiveController와 같은 클래스에 합칠 필요는 없음(JWT 인증은 전역 필터+URL 패턴 기준이라
// 컨트롤러 클래스와 무관) — 대신 확정 스펙인 /api/archive/insight/{weekStart} URL만 그대로 맞춤.
@RestController
@RequestMapping("/api/archive")
@RequiredArgsConstructor
public class InsightController {

    private final InsightService insightService;
    private final JsonMapper jsonMapper;

    @GetMapping(value = "/insight/{weekStart}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWeeklyInsight(
            @AuthenticationPrincipal User user,
            @PathVariable LocalDate weekStart
    ) {
        // aiResult는 FastAPI가 내려준 WeeklyReportResponse를 그대로 JSON 문자열로 저장해둔 것이라
        // DTO로 재역직렬화하지 않고 그대로 반환. 리포트가 없으면(기록 부족 등) status만 담은 응답.
        return insightService.getWeeklyInsightRaw(user.getUserId(), weekStart)
                .orElseGet(() -> jsonMapper.writeValueAsString(new NoReportResponse()));
    }
}
