package org.example.commercebackoffice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST,"PASSWORD INCORRECT", "비밀번호가 일치하지 않습니다."),
    USER_NOT_ACTIVE(HttpStatus.UNAUTHORIZED, "USER NOT ACTIVE", "해당 계정은 활성화 상태가 아닙니다."),
    USER_STATUS_NOT_PENDING(HttpStatus.BAD_REQUEST, "USER IS NOT PENDING", "해당 계정은 승인 대기 상태가 아닙니다."),
    USER_IS_NOT_SUPER_ADMIN(HttpStatus.UNAUTHORIZED, "USER IS NOT SUPER ADMIN", "해당 계정은 슈퍼 관리자 계정이 아닙니다."),
    UNKNOWN_STATUS(HttpStatus.BAD_REQUEST, "UNKNOWN STATUS", "상태 목록에 없는 상태입니다. %s"),
    UNKNOWN_ROLE(HttpStatus.BAD_REQUEST, "UNKNOWN ROLE", "역할 목록에 없는 역할입니다. %s"),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "CUSTOMER NOT FOUND", "해당 고객을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"REVIEW NOT FOUND","리뷰를 찾을 수 없습니다"),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM NOT FOUND", "상품을 찾을 수 없습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN NOT FOUND", "관리자를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER NOT FOUND", "주문을 찾을 수 없습니다."),
    INVALID_ORDER_QUANTITY(HttpStatus.BAD_REQUEST, "INVALID ORDER QUANTITY", "주문 수량은 1개 이상이어야 합니다."),
    INVALID_ORDER_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "INVALID ORDER STATUS TRANSITION", "잘못된 상태 변경 순서입니다."),
    ORDER_CANCEL_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "ORDER CANCEL NOT ALLOWED", "준비중 상태에서만 취소할 수 있습니다."),
    ITEM_NOT_ON_SALE(HttpStatus.BAD_REQUEST, "ITEM NOT ON SALE", "현재 판매 중인 상품이 아닙니다."),
    ITEM_STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "ITEM STOCK NOT ENOUGH", "상품의 재고가 부족합니다."),
    INVALID_STOCK_QUANTITY(HttpStatus.BAD_REQUEST, "INVALID RESTORATION QUANTITY", "복구할 재고 수량은 0보다 커야 합니다."),
    ITEM_NOT_FOUND_OR_UNMODIFIABLE(HttpStatus.BAD_REQUEST, "ITEM_NOT_FOUND_OR_UNMODIFIABLE", "존재하지 않거나 수정할 수 없는 상품입니다."),
    CUSTOMER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "CUSTOMER ALREADY DELETED", "이미 탈퇴 처리되었거나 비활성화된 회원입니다."),
    ALREADY_REVIEWED_ORDER(HttpStatus.BAD_REQUEST, "ALREADY REVIEWED ORDER", "이미 리뷰를 작성한 주문입니다.");
    
    
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