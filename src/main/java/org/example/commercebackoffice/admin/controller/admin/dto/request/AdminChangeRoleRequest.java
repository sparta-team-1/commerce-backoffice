package org.example.commercebackoffice.admin.controller.admin.dto.request;

import org.example.commercebackoffice.admin.domain.enums.AdminRole;

public record AdminChangeRoleRequest(
        AdminRole role
) {
}
