package com.moonment.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponse {

    private Integer questionId;

    private String type;

    private String content;
}
