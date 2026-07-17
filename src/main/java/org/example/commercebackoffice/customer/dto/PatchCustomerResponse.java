package org.example.commercebackoffice.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

@Schema(description = "고객 상태 수정 응답 DTO")
@Getter
public class PatchCustomerResponse {

    @Schema(description = "고객 ID", example = "1")
    private final Long id;

    @Schema(description = "변경된 고객 상태", example = "ACTIVE")
    private final CustomerStatus status;

    public PatchCustomerResponse(Long id, CustomerStatus status) {
        this.id = id;
        this.status = status;
    }
}