package org.example.commercebackoffice.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "주문 취소 요청 DTO")
@Getter
@NoArgsConstructor
public class OrderCancelRequestDto {

    @Schema(description = "주문 취소 사유", example = "고객 단순 변심")
    @NotBlank
    private String cancelReason;
}