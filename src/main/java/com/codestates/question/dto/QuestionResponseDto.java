package com.codestates.question.dto;

import com.codestates.answer.dto.AnswerResponseDto;
import com.codestates.answer.entity.Answer;
import com.codestates.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionResponseDto {
    private String title;
    private String content;
    private String name;
    private LocalDateTime createdAt;
    private Question.QuestionDisclosure questionDisclosure;
    private Question.QuestionStatus questionStatus;
    private List<AnswerResponseDto> answers;
}
