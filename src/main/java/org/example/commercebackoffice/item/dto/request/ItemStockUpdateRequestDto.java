package org.example.commercebackoffice.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "상품 재고 수정 요청 DTO")
@Getter
public class ItemStockUpdateRequestDto {

    @Schema(description = "변경할 재고 수량", example = "50")
    @NotNull(message = "변경할 재고 수량을 입력해주세요.")
    @Min(value = 0, message = "재고 수량은 0개 이상이어야 합니다.")
    private Integer stock;
}