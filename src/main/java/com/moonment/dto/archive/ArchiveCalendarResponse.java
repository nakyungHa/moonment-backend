package com.moonment.dto.archive;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArchiveCalendarResponse {

    private Integer year;

    private Integer month;

    private List<ArchiveCalendarDayResponse> days;

}
