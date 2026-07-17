package org.example.commercebackoffice.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "주문 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    @Schema(description = "고객 ID", example = "1")
    @NotNull
    private Long customerId;

    @Schema(description = "상품 ID", example = "10")
    @NotNull
    private Long itemId;

    @Schema(description = "주문 수량", example = "2")
    @NotNull
    @Min(1)
    private Integer quantity;
}