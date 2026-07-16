package org.example.commercebackoffice.admin.controller.admin.dto.request;

import org.example.commercebackoffice.admin.domain.enums.AdminStatus;

public record AdminChangeStatusRequest(
        String status
) {
}
