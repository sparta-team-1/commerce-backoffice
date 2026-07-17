package org.example.commercebackoffice.review.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "리뷰 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ReviewCreateRequestDto {

    @Schema(description = "주문 ID", example = "1")
    @NotNull
    private Long orderId;

    @Schema(description = "평점", example = "5", minimum = "1", maximum = "5")
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @Schema(description = "리뷰 내용", example = "배송이 빠르고 상품 품질이 매우 만족스럽습니다.")
    @NotBlank
    private String content;
}