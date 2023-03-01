package com.codestates.question.mapper;


import com.codestates.member.entity.Member;
import com.codestates.question.dto.QuestionPostDto;
import com.codestates.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto) {
        Question question = new Question();
        Member member = new Member();
        member.setMemberId(questionPostDto.getMemberId());


        question.setMember(member);
        question.setTitle(questionPostDto.getTitle());
        question.setContent(questionPostDto.getContent());
        if (questionPostDto.getDisclosure().equals("PUBLIC")) {
            question.setQuestionDisclosure(Question.QuestionDisclosure.QUESTION_PUBLIC);
        } else if (questionPostDto.getDisclosure().equals("SECURE")) {
            question.setQuestionDisclosure(Question.QuestionDisclosure.QUESTION_SECRET);
        }

        return question;
    }
}
