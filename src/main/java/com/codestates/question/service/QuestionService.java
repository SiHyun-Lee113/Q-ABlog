package com.codestates.question.service;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.service.MemberService;
import com.codestates.question.entity.Question;
import com.codestates.question.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

@Slf4j
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

    public Question updateQuestion(Question question) {
        // 질문 게시글 주인과 요청을 날린 주인이 같은지 확인
        checkQuestionOwner(question);

        // 답변 여부 체크
        checkAnswered(question);

        Question findQuestion = findVerifiedQuestion(question.getQuestionId());
        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);
        Optional.ofNullable(question.getQuestionDisclosure())
                .ifPresent(findQuestion::setQuestionDisclosure);
        if (findQuestion.getQuestionStatus() != null) {
            question.setQuestionStatus(findQuestion.getQuestionStatus());
        }

        return questionRepository.save(question);
    }

    public Page<Question> findQuestions(int page, int size, long memberId) {
        Page<Question> questions = questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));
        Iterator<Question> iterator = questions.iterator();

        Member member = memberService.findMember(memberId);
        if (member.getEmail().equals("admin@gmail.com")) {
            return questions;
        }

        while (iterator.hasNext()) {
            Question next = iterator.next();
            if (next.getQuestionDisclosure().equals(Question.QuestionDisclosure.QUESTION_SECRET)) {
                if (!next.getMember().getMemberId().equals(memberId)) {
                    iterator.remove();
                }
            }
        }

        return questions;
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findVerifiedQuestion(questionId);
        findQuestion.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);
        questionRepository.save(findQuestion);
    }

    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        if (findQuestion.getQuestionStatus() == Question.QuestionStatus.QUESTION_DELETE) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_IS_DELETED);
        }
        return findQuestion;
    }
    public void verifyQuestion(Question question) {
        memberService.findVerifiedMember(question.getMember().getMemberId());
    }

    public void checkQuestionOwner(Question question) {
        Optional<Question> optionalQuestion = questionRepository.findById(question.getQuestionId());
        Question findQuestion = optionalQuestion.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        Member findMember = memberService.findMember(question.getMember().getMemberId());

        if (!(Objects.equals(findQuestion.getMember().getMemberId(), question.getMember().getMemberId()) ||
                        Objects.equals(findMember.getEmail(), "admin@gmail.com"))) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_NO_PERMISSION);
        }
    }

    public boolean verifyQuestionOwner(long memberId, long questionId) {
        Question question = new Question();
        Member member = new Member();
        member.setMemberId(memberId);
        question.setQuestionId(questionId);
        question.setMember(member);

        checkQuestionOwner(question);

        return true;
    }

    public void checkAnswered(Question question) {
        Optional<Question> optionalQuestion = questionRepository.findById(question.getQuestionId());
        Question findQuestion = optionalQuestion.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        int step = findQuestion.getQuestionStatus().getStep();

        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_ALREADY_ANSWERED);
        }
    }


}
