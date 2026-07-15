package com.moonment.dto.insight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class InsightDto {

    private InsightDto() {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record WeeklyReportRequest(
            @JsonProperty("user_id") UUID userId,
            // 리포트 이미지(B5)에 "OOO님의 O월 O주차" 형태로 표시하기 위한 사용자 이름.
            @JsonProperty("user_name") String userName,
            @JsonProperty("week_start") LocalDate weekStart,
            @JsonProperty("user_context") String userContext,
            // Spring이 DB 기준으로 계산한 값. Python은 이 값을 그대로 써야 함(B3 — 양쪽에서 따로 세지 않기 위함).
            @JsonProperty("record_count") Integer recordCount,
            @JsonProperty("daily_entries") List<DailyEntry> dailyEntries
    ) {}

    public record DailyEntry(
            @JsonProperty("record_date") LocalDate recordDate,
            List<AnswerPayload> answers
    ) {}

    public record AnswerPayload(
            @JsonProperty("question_id") Integer questionId,
            @JsonProperty("question_type") String questionType,
            @JsonProperty("content_main") String contentMain,
            @JsonProperty("content_sub") String contentSub,
            @JsonProperty("is_skipped") boolean isSkipped
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WeeklyReportResponse(
            String status,
            // 프론트 리포트 화면 헤더 "OOO님의 O월 O주차"에 쓰이는 사용자 이름.
            @JsonProperty("user_name") String userName,
            @JsonProperty("week_start") LocalDate weekStart,
            List<String> keywords,
            @JsonProperty("summary_message") String summaryMessage,
            Stats stats,
            List<InsightItem> insights,
            List<Recommendation> recommendations,
            @JsonProperty("emotion_graph") List<EmotionPoint> emotionGraph,
            @JsonProperty("generation_meta") GenerationMeta generationMeta,
            @JsonProperty("error_code") String errorCode,
            @JsonProperty("error_message") String errorMessage,
            @JsonProperty("retry_suggested") Boolean retrySuggested,
            // 데모용 리포트 이미지(B5). base64 순수 데이터, 없으면 null.
            @JsonProperty("report_image_base64") String reportImageBase64
    ) {
        public boolean isCompleted() {
            return "completed".equals(status);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Stats(
            @JsonProperty("total_records") Integer totalRecords,
            @JsonProperty("avg_emotion") Double avgEmotion,
            @JsonProperty("emotion_volatility") Double emotionVolatility,
            @JsonProperty("emotion_trend") String emotionTrend,
            @JsonProperty("low_streak") Integer lowStreak
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record InsightItem(String title, List<String> bullets) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Recommendation(String icon, String title, String description) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record EmotionPoint(LocalDate date, Integer score) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GenerationMeta(
            String model,
            @JsonProperty("input_tokens") Integer inputTokens,
            @JsonProperty("output_tokens") Integer outputTokens,
            @JsonProperty("cached_tokens") Integer cachedTokens,
            @JsonProperty("duration_ms") Integer durationMs
    ) {}

    public record NoReportResponse(String status) {
        public NoReportResponse() {
            this("no_report");
        }
    }
}
