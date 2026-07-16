package org.example.commercebackoffice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST,"PASSWORD INCORRECT", "비밀번호가 일치하지 않습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE NOT FOUND", "해당 일정을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT NOT FOUND", "해당 댓글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND", "사용자를 찾을 수 없습니다."),
    NOT_AUTHOR(HttpStatus.UNAUTHORIZED, "NOT AUTHOR", "작성자가 아닙니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "접근이 거부되었습니다."),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "CUSTOMER NOT FOUND", "해당 고객을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"REVIEW NOT FOUND","리뷰를 찾을 수 없습니다"),
    //주문 에러 메세지
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM NOT FOUND", "상품을 찾을 수 없습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN NOT FOUND", "관리자를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER NOT FOUND", "주문을 찾을 수 없습니다."),
    INVALID_ORDER_QUANTITY(HttpStatus.BAD_REQUEST, "INVALID ORDER QUANTITY", "주문 수량은 1개 이상이어야 합니다."),
    INVALID_ORDER_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "INVALID ORDER STATUS TRANSITION", "잘못된 상태 변경 순서입니다."),
    ORDER_CANCEL_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "ORDER CANCEL NOT ALLOWED", "준비중 상태에서만 취소할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }


    public String message(Object... args) {
        return String.format(message, args);
    }
}