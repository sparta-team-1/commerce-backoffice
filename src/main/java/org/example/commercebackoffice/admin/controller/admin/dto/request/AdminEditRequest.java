package org.example.commercebackoffice.admin.controller.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Schema(description = "관리자 정보 수정 DTO")
public record AdminEditRequest(
        @Schema(description = "이름", example = "test")
        String name,
        @Schema(description = "이메일", example = "test@email.com")
        @Email
        String email,
        @Schema(description = "전화번호", example = "010-1234-5678")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
        String phone
) {
}
