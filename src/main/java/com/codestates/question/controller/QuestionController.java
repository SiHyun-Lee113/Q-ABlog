package com.codestates.question.controller;

import com.codestates.dto.MultiResponseDto;
import com.codestates.dto.SingleResponseDto;
import com.codestates.question.dto.QuestionPatchDto;
import com.codestates.question.dto.QuestionPostDto;
import com.codestates.question.entity.Question;
import com.codestates.question.mapper.QuestionMapper;
import com.codestates.question.service.QuestionService;
import com.codestates.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v11/questions")
@Validated
public class QuestionController {
    private final static String QUESTION_DEFAULT_URL = "/v11/questions";
    private final QuestionService questionService;
    private final QuestionMapper mapper;


    public QuestionController(QuestionService questionService, QuestionMapper mapper) {
        this.questionService = questionService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto) {
        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto));
        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, question.getQuestionId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping
    public ResponseEntity patchQuestion(@Valid @RequestBody QuestionPatchDto questionPatchDto) {
        Question question = questionService.updateQuestion(mapper.questionPatchDtoToQuestion(questionPatchDto));
        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, question.getQuestionId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{question_id}")
    public ResponseEntity getQuestion(@PathVariable("question_id") @Positive long question_id) {
        Question question = questionService.findVerifiedQuestion(question_id);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getQuestions(@RequestParam("member_id") @Positive long member_id,
                                       @RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {
        Page<Question> questions = questionService.findQuestions(page - 1, size, member_id);
        List<Question> content = questions.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(mapper.questionsToQuestionResponseDtos(content), questions), HttpStatus.OK);
    }

    @DeleteMapping("/{question_id}")
    public void deleteQuestion(@PathVariable("question_id") @Positive long question_id) {
        questionService.deleteQuestion(question_id);
    }
}
