package org.example.commercebackoffice.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "상품 정보 수정 요청 DTO")
@Getter
public class ItemUpdateRequestDto {

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
}