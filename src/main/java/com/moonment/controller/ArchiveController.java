package com.moonment.controller;

import com.moonment.dto.archive.ArchiveCalendarResponse;
import com.moonment.dto.archive.ArchiveDayStatusResponse;
import com.moonment.entity.User;
import com.moonment.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    @GetMapping("/calendar")
    public ArchiveCalendarResponse getCalendar(
            @AuthenticationPrincipal User user,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        return archiveService.getCalendar(user, year, month);
    }

    @GetMapping("/{date}")
    public ArchiveDayStatusResponse getDayStatus(
            @AuthenticationPrincipal User user,
            @PathVariable LocalDate date
    ) {
        return archiveService.getDayStatus(user, date);
    }
}
