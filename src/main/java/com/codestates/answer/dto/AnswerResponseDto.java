package com.codestates.answer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerResponseDto {

    private long memberId;
    private String name;
    private String content;
    private LocalDateTime localDateTime;
}
