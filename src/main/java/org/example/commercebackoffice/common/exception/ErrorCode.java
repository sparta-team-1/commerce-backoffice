package org.example.commercebackoffice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST,"PASSWORD INCORRECT", "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ADMIN NOT FOUND", "사용자를 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "접근이 거부되었습니다."),
    EMAIL_IN_USE(HttpStatus.CONFLICT, "EMAIL IN USE", "이미 사용 중인 이메일입니다."),
    USER_NOT_ACTIVE(HttpStatus.UNAUTHORIZED, "USER NOT ACTIVE", "해당 계정은 활성화 상태가 아닙니다."),
    USER_STATUS_NOT_PENDING(HttpStatus.BAD_REQUEST, "USER IS NOT PENDING", "해당 계정은 승인 대기 상태가 아닙니다."),
    USER_IS_NOT_SUPER_ADMIN(HttpStatus.UNAUTHORIZED, "USER IS NOT SUPER ADMIN", "해당 계정은 슈퍼 관리자 계정이 아닙니다."),
    UNKNOWN_STATUS(HttpStatus.BAD_REQUEST, "UNKNOWN STATUS", "상태 목록에 없는 상태입니다."),
    UNKNOWN_ROLE(HttpStatus.BAD_REQUEST, "UNKNOWN ROLE", "역할 목록에 없는 역할입니다.");

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