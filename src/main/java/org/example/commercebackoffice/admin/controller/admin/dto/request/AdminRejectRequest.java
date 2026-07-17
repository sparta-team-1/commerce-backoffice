package org.example.commercebackoffice.admin.controller.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "관리자 계정 거부 DTO")
public record AdminRejectRequest(
        @Schema(description = "거부 메시지", example = "경력 부족")
        @NotBlank
        String rejectionMessage
) {
}
