package com.moonment.dto.answer;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
public class SaveAnswerRequest {
    //프론트가 보내는 전체 요청
    private UUID userId; // jwt 붙이고 난 뒤에 삭제
    private LocalDate recordDate;

    private List<AnswerItemRequest> answers;
}
