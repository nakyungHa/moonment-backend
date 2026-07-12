package com.moonment.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class AnswerListResponse {

    private LocalDate recordDate;

    private Boolean hasRecord; //추가

    private List<AnswerResponse> answers;
}
