package org.example.commercebackoffice.admin.controller.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;

import java.time.LocalDateTime;

@Schema(description = "관리자 상세 정보 DTO")
public record AdminDetailResponse (
        @Schema(description = "id", example = "1")
        Long id,
        @Schema(description = "이름", example = "test")
        String name,
        @Schema(description = "이메일", example = "test@email.com")
        String email,
        @Schema(description = "전화번호", example = "010-12334-5678")
        String phone,
        @Schema(description = "역할", example = "CS_ADMIN")
        AdminRole role,
        @Schema(description = "상태", example = "ACTIVE")
        AdminStatus status,
        @Schema(description = "거부 사유", example = "그냥")
        @Nullable
        String rejectionReason,
        @Schema(description = "생성일", example = "2026-07-16T00:00:00")
        LocalDateTime createdAt,
        @Schema(description = "승인일", example = "2026-07-16T00:00:00")
        @Nullable
        LocalDateTime approvedAt,
        @Schema(description = "거부일", example = "2026-07-16T00:00:00")
        @Nullable
        LocalDateTime rejectedAt
) {
}
