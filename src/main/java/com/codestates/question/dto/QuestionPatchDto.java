package com.codestates.question.dto;

import com.codestates.member.entity.Member;
import com.codestates.question.entity.Question;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Getter
public class QuestionPatchDto {

    @Positive
    private long memberId;

    @Positive
    private long questionId;

    private String title;

    private String content;

    private Question.QuestionDisclosure questionDisclosure;

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }

}
