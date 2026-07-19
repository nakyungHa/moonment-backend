package com.moonment.dto.answer;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SaveAnswerRequest {
    //프론트가 보내는 전체 요청
    private LocalDate recordDate;

    private List<AnswerItemRequest> answers;
}
