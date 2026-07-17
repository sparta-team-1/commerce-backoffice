package org.example.commercebackoffice.admin.controller.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;

@Schema(description = "관리자 상태 변경 DTO")
public record AdminChangeStatusRequest(
        @Schema(description = "변경할 상태", example = "ACTIVE")
        String status
) {
}
