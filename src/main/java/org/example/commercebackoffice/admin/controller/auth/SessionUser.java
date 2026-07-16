package org.example.commercebackoffice.admin.controller.auth;

import org.example.commercebackoffice.admin.domain.enums.AdminRole;

public record SessionUser(
        Long id,
        String email,
        AdminRole role
) {
}
