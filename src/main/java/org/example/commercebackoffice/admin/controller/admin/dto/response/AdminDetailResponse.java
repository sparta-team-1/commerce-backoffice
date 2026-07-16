package org.example.commercebackoffice.admin.controller.admin.dto.response;

import jakarta.annotation.Nullable;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;

import java.time.LocalDateTime;

public record AdminDetailResponse (
        Long id,
        String name,
        String email,
        String phone,
        AdminRole role,
        AdminStatus status,
        @Nullable
        String rejectionReason,
        LocalDateTime createdAt,
        @Nullable
        LocalDateTime approvedAt,
        @Nullable
        LocalDateTime rejectedAt
) {
}
