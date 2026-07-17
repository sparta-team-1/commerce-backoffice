package org.example.commercebackoffice.admin.controller.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관리자 역할 변경 DTO")
public record AdminChangeRoleRequest(
        @Schema(description = "변경할 역할", example = "CS_ADMIN")
        String role
) {
}
