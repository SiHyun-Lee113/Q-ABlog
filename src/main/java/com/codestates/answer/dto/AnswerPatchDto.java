package com.codestates.answer.dto;

import com.codestates.member.entity.Member;
import com.codestates.question.entity.Question;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class AnswerPatchDto {

    @Positive
    private long questionId;

    @Positive
    private long memberId;

    private String content;

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }

    public Question getQuestion() {
        Question question = new Question();
        question.setQuestionId(questionId);
        return question;
    }
}
