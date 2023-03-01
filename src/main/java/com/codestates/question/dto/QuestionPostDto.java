package com.codestates.question.dto;

import com.codestates.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class QuestionPostDto {
    @Positive
    private long memberId;

    @NotBlank(message = "제목은 공백이 아니어야 합니다.")
    private String title;

    @NotBlank(message = "내용은 공백이 아니어야 합니다.")
    private String content;

    @NotBlank(message = "공개 여부를 설정해 주세요")
    private String disclosure;
}
