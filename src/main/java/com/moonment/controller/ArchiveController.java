package com.moonment.controller;

import com.moonment.dto.archive.ArchiveCalendarResponse;
import com.moonment.dto.archive.ArchiveDayStatusResponse;
import com.moonment.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    public ArchiveCalendarResponse getCalendar(
            @RequestParam UUID userId,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        return archiveService.getCalendar(userId, year, month);
    }

    public ArchiveDayStatusResponse getDatStatue (
            @PathVariable LocalDate date,
            @RequestParam UUID userId
    ) {
        return archiveService.getDayStatus(userId, date);
    }
}
