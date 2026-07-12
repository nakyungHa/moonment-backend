package com.moonment.dto.answer;

import lombok.Getter;

@Getter
public class AnswerItemRequest {
    //질문 하나에 대한 답변을 저장하는 dto
    private Integer questionId;

    private String content;
}
