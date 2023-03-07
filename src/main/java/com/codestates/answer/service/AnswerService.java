package com.codestates.answer.service;

import com.codestates.answer.entity.Answer;
import com.codestates.answer.repository.AnswerRepository;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.service.MemberService;
import com.codestates.question.entity.Question;
import com.codestates.question.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberService memberService;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, MemberService memberService, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.memberService = memberService;
        this.questionService = questionService;
    }

    public Answer createAnswer(Answer answer) {
        // 답변은 질문 작성자, 관리자만 달 수 있다.
        verifyWriter(answer);

        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        verifyAnswerWriter(answer);

        Answer findAnswer = findVerifiedAnswer(answer);
        findAnswer.setContent(answer.getContent());

        return answerRepository.save(findAnswer);
    }

    public void deleteAnswer(long answerId, long memberId) {
        Answer answer = new Answer();
        answer.setAnswerId(answerId);

        Answer deleteAnswer = findVerifiedAnswer(answer);

        if (deleteAnswer.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.ANSWER_NO_PERMISSION);
        }

        answerRepository.delete(deleteAnswer);
    }

    public Answer findVerifiedAnswer(Answer answer) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answer.getAnswerId());
        Answer findAnswer = optionalAnswer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return findAnswer;
    }

    public void verifyAnswerWriter(Answer answer) {
        Answer verifiedAnswer = findVerifiedAnswer(answer);

        if (!Objects.equals(verifiedAnswer.getMember().getMemberId(), answer.getMember().getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.ANSWER_NO_PERMISSION);
        }
    }

    public void verifyWriter(Answer answer) {
        long questionId = answer.getQuestion().getQuestionId();
        Long memberId = answer.getMember().getMemberId();

        if (!(questionService.verifyQuestionOwner(memberId, questionId))) {
            throw new BusinessLogicException(ExceptionCode.ANSWER_NO_PERMISSION);
        }
    }
}
