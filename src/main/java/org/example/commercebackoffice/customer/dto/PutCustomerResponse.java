package org.example.commercebackoffice.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "고객 정보 수정 응답 DTO")
@Getter
public class PutCustomerResponse {

    @Schema(description = "고객 ID", example = "1")
    private final Long id;

    @Schema(description = "고객명", example = "홍길동")
    private final String name;

    @Schema(description = "이메일", example = "hong@example.com")
    private final String email;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private final String phone;

    public PutCustomerResponse(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}