package org.example.commercebackoffice.admin.controller.admin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record AdminEditRequest(
        String name,
        @Email
        String email,
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
        String phone
) {
}
