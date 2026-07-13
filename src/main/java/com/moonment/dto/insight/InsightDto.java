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
            @JsonProperty("week_start") LocalDate weekStart,
            @JsonProperty("user_context") String userContext,
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
            @JsonProperty("retry_suggested") Boolean retrySuggested
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
