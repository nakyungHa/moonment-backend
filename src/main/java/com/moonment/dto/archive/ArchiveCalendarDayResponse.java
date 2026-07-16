package com.moonment.dto.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ArchiveCalendarDayResponse {

    private LocalDate date;

    private Boolean hasRecord;

    private Integer moonLevel;
}
