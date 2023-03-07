package com.codestates.answer.controller;

import com.codestates.answer.dto.AnswerPatchDto;
import com.codestates.answer.dto.AnswerPostDto;
import com.codestates.answer.entity.Answer;
import com.codestates.answer.mapper.AnswerMapper;
import com.codestates.answer.service.AnswerService;
import com.codestates.utils.UriCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/v11/answers")
@Validated
public class AnswerController {

    private final static String ANSWER_DEFAULT_URL = "/v11/answers";
    private final AnswerService answerService;
    private final AnswerMapper mapper;

    public AnswerController(AnswerService answerService, AnswerMapper mapper) {
        this.answerService = answerService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto) {
        Answer answer = answerService.createAnswer(mapper.answerPostDtoToAnswer(answerPostDto));
        URI location = UriCreator.createUri(ANSWER_DEFAULT_URL, answer.getAnswerId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity patchAnswer(@PathVariable long answerId,
                                      @Valid @RequestBody AnswerPatchDto answerPatchDto) {
        Answer answerDtoToAnswer = mapper.answerPatchDtoToAnswer(answerPatchDto);
        answerDtoToAnswer.setAnswerId(answerId);

        Answer answer = answerService.updateAnswer(answerDtoToAnswer);
        URI location = UriCreator.createUri(ANSWER_DEFAULT_URL, answer.getAnswerId());

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/{answer_id}")
    public void deleteAnswer(@PathVariable @Positive long answer_id,
                             @RequestParam("member_id") @Positive long member_id) {
        answerService.deleteAnswer(answer_id, member_id);
    }
}
