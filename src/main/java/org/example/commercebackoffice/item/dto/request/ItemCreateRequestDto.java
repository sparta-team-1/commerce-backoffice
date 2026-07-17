package org.example.commercebackoffice.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.commercebackoffice.item.domain.enums.ItemStatus;

@Schema(description = "상품 생성 요청 DTO")
@Getter
public class ItemCreateRequestDto {

    @Schema(description = "관리자 ID", example = "1")
    @NotNull(message = "관리자 ID는 필수입니다.")
    private Long adminId;

    @Schema(description = "상품명", example = "무선 마우스")
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "카테고리", example = "전자기기")
    @NotBlank(message = "카테고리는 필수 입력 값입니다.")
    private String category;

    @Schema(description = "상품 가격", example = "35000")
    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Long price;

    @Schema(description = "재고 수량", example = "100")
    @NotNull(message = "재고 수량은 필수 입력 값입니다.")
    @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
    private Integer stock;

    @Schema(description = "상품 상태", example = "ON_SALE")
    @NotNull(message = "상품 상태는 필수 입력 값입니다.")
    private ItemStatus status;
}