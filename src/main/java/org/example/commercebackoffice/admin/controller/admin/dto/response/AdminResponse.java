package org.example.commercebackoffice.admin.controller.admin.dto.response;

import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;

import java.time.LocalDateTime;

public record AdminResponse (
        Long id,
        String email,
        String name,
        String phoneNumber,
        AdminRole adminRole,
        AdminStatus status,
        LocalDateTime createdAt,
        LocalDateTime approvedAt
) {
}
