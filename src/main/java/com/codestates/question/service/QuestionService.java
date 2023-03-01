package com.codestates.question.service;

import com.codestates.member.entity.Member;
import com.codestates.member.service.MemberService;
import com.codestates.question.entity.Question;
import com.codestates.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository, MemberService memberService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question) {
        verifyQuestion(question);

        return questionRepository.save(question);
    }

    public void verifyQuestion(Question question) {
        memberService.findVerifiedMember(question.getMember().getMemberId());
    }
}
