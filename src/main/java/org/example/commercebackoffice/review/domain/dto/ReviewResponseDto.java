package org.example.commercebackoffice.review.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.review.domain.Review;

@Schema(description = "리뷰 응답 DTO")
@Getter
public class ReviewResponseDto {

    @Schema(description = "리뷰 ID", example = "1")
    private final Long id;

    @Schema(description = "평점", example = "5")
    private final Integer rating;

    @Schema(description = "리뷰 내용", example = "배송이 빠르고 상품 품질이 매우 만족스럽습니다.")
    private final String content;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.content = review.getContent();
    }
}