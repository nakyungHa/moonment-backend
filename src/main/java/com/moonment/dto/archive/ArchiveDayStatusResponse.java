package com.moonment.dto.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ArchiveDayStatusResponse {

    private LocalDate date;

    private Boolean hasRecord;

    private Boolean canViewInsight;

    private LocalDate weekStart;

}
