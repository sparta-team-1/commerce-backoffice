package org.example.commercebackoffice.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
//ErrorResponse는 데이터를 담는 DTO이다.
//클라이언트에게 항상 동일한 joson 형태로 에러를 전달하기 위해 DTO를 만든다.
@Getter
@Schema(description = "공통 에러 응답")
//@AllArgsConstructor
public class ErrorResponse {
    @Schema(description = "HTTP 상태 코드", example = "404")
    private final int status; //HTTP 상태코드
    @Schema(description = "에러 메시지", example = "ADMIN_NOT_FOUND")
    private final  String message; //에러 메시지
    @Schema(description = "발생 시간", example = "2026-07-16T00:00:00.00000")
    private final LocalDateTime time; //에러가 발생한 시간을 저장

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.time = LocalDateTime.now();
    }
}
