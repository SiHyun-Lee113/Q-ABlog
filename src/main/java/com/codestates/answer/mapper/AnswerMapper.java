package com.codestates.answer.mapper;

import com.codestates.answer.dto.AnswerPostDto;
import com.codestates.answer.dto.AnswerResponseDto;
import com.codestates.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {


    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);

    AnswerResponseDto answerToAnswerResponseDto(Answer answer);

}
