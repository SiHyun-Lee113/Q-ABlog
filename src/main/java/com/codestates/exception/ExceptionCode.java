package com.codestates.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    COFFEE_NOT_FOUND(404, "Coffee not found"),
    COFFEE_CODE_EXISTS(409, "Coffee Code exists"),
    ORDER_NOT_FOUND(404, "Order not found"),
    CANNOT_CHANGE_ORDER(403, "Order can not change"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    QUESTION_NOT_FOUND(1004, "Question not found"),
    QUESTION_ALREADY_ANSWERED(1005, "This Question is Already Answered"),
    QUESTION_NO_PERMISSION(1006, "No permission to edit Question posts"),
    QUESTION_IS_DELETED(1008, "This post already DELETED!!"),
    ANSWER_NO_PERMISSION(1007, "No permission to reply to Question posts "),
    ANSWER_NOT_FOUND(1009, "Answer not found");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
