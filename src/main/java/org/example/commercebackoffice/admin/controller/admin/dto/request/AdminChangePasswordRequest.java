package org.example.commercebackoffice.admin.controller.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminChangePasswordRequest(
        @Size(min = 8, max = 255)
        @NotBlank
        String password
) {
}
