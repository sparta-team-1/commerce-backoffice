package org.example.commercebackoffice.admin.controller.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "관리자 비밀번호 변경 DTO")
public record AdminChangePasswordRequest(
        @Schema(description = "현재 비밀번호", example = "password")
        @Size(min = 8, max = 255)
        @NotBlank
        String password,
        @Schema(description = "새 비밀번호", example = "newPassword")
        @Size(min = 8, max = 255)
        @NotBlank
        String newPassword
) {
}
