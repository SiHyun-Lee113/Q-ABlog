package com.codestates.question.mapper;


import com.codestates.answer.dto.AnswerResponseDto;
import com.codestates.answer.entity.Answer;
import com.codestates.member.entity.Member;
import com.codestates.order.dto.OrderResponseDto;
import com.codestates.order.entity.Order;
import com.codestates.question.dto.QuestionPatchDto;
import com.codestates.question.dto.QuestionPostDto;
import com.codestates.question.dto.QuestionResponseDto;
import com.codestates.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);

    List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions);


    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);

    default QuestionResponseDto questionToQuestionResponseDto(Question question) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        List<AnswerResponseDto> answers = new ArrayList<>();

        for (Answer answer : question.getAnswers()) {
            AnswerResponseDto answerResponseDto = new AnswerResponseDto();
            answerResponseDto.setMemberId(answer.getMember().getMemberId());
            answerResponseDto.setContent(answer.getContent());
            answerResponseDto.setName(answer.getMember().getName());
            answerResponseDto.setLocalDateTime(answer.getCreatedAt());
            answers.add(answerResponseDto);
        }

        questionResponseDto.setAnswers(answers);
        questionResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionResponseDto.setQuestionDisclosure(question.getQuestionDisclosure());
        questionResponseDto.setName(question.getMember().getName());
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setCreatedAt(question.getCreatedAt());
        return questionResponseDto;
    }
}
