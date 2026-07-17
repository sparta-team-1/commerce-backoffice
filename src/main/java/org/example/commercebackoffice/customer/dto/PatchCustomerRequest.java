package org.example.commercebackoffice.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

@Schema(description = "고객 상태 수정 요청 DTO")
@Getter
public class PatchCustomerRequest {

    @Schema(description = "변경할 고객 상태", example = "ACTIVE")
    private CustomerStatus status;
}