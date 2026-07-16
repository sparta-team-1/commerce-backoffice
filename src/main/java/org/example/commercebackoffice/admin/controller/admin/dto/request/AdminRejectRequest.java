package org.example.commercebackoffice.admin.controller.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AdminRejectRequest(
        @NotBlank
        String rejectionMessage
) {
}
