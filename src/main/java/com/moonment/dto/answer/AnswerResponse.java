package com.moonment.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerResponse {

    private Integer questionId;

    private String questionType; //추가

    private String question;

    private String content;
}
