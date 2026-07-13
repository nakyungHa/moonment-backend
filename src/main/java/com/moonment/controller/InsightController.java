package com.moonment.controller;

import com.moonment.service.InsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
public class InsightController {

    private static final String NO_REPORT_BODY = "{\"status\":\"no_report\"}";

    private final InsightService insightService;

    @GetMapping("/{userId}/weekly")
    public ResponseEntity<String> getWeeklyInsight(
            @PathVariable UUID userId,
            @RequestParam("weekStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart) {

        String body = insightService.getWeeklyInsightRaw(userId, weekStart).orElse(NO_REPORT_BODY);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
