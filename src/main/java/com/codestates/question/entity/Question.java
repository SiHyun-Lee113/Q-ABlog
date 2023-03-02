package com.codestates.question.entity;

import com.codestates.answer.entity.Answer;
import com.codestates.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_REGISTRATION;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 30, nullable = false)
    private QuestionDisclosure questionDisclosure;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Answer> answers = new ArrayList<>();

    public enum QuestionStatus {
        QUESTION_REGISTRATION("질문 등록", 1),
        QUESTION_ANSWERED("답변 완료", 2),
        QUESTION_DELETE("질문 삭제!", 3),
        ;

        @Getter
        private String status;

        @Getter
        private int step;

        QuestionStatus(String status, int step) {
            this.status = status;
            this.step = step;
        }
    }

    public enum QuestionDisclosure {
        QUESTION_PUBLIC("공개글"),
        QUESTION_SECRET("비밀글");

        @Getter
        private String disclosure;

        QuestionDisclosure(String disclosure) {
            this.disclosure = disclosure;
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", questionStatus=" + questionStatus +
                ", questionDisclosure=" + questionDisclosure +
                ", createdAt=" + createdAt +
                ", member=" + member +
                '}';
    }
}
