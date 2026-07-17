package org.example.commercebackoffice.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "고객 정보 수정 요청 DTO")
@Getter
public class PutCustomerRequest {

    @Schema(description = "고객명", example = "홍길동")
    private String name;

    @Schema(description = "이메일", example = "hong@example.com")
    private String email;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private String phone;
}