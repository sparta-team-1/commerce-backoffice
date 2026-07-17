package org.example.commercebackoffice.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.commercebackoffice.item.domain.enums.ItemStatus;

@Schema(description = "상품 상태 수정 요청 DTO")
@Getter
public class ItemStatusUpdateRequestDto {

    @Schema(description = "변경할 상품 상태", example = "ON_SALE")
    @NotNull(message = "변경할 상태값을 입력해주세요.")
    private ItemStatus status;
}