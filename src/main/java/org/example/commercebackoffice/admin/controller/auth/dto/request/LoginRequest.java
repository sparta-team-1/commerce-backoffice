package org.example.commercebackoffice.admin.controller.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "로그인 요청 DTO")
public record LoginRequest(
        @Schema(description = "이메일", example = "test@email.com")
        @Email
        @NotBlank
        String email,
        @Schema(description = "비밀번호", example = "password")
        @NotBlank
        @Size(min = 8)
        String password
) {
}
