package org.example.commercebackoffice.admin.controller.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;

import java.time.LocalDateTime;

@Schema(description = "관리자 정보 DTO")
public record AdminResponse (
        @Schema(description = "id", example = "1")
        Long id,
        @Schema(description = "email", example = "test@email.com")
        String email,
        @Schema(description = "이름", example = "test")
        String name,
        @Schema(description = "전화번호", example = "010-1234-5678")
        String phoneNumber,
        @Schema(description = "역할", example = "SUPER_ADMIN")
        AdminRole adminRole,
        @Schema(description = "상태", example = "ACTIVE")
        AdminStatus status,
        @Schema(description = "생성일", example = "2026-07-16T00:00:00")
        LocalDateTime createdAt,
        @Schema(description = "승인일", example = "2026-07-16T00:00:00")
        LocalDateTime approvedAt
) {
}
