package org.example.commercebackoffice.review.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.review.domain.Review;

import java.time.LocalDateTime;

@Schema(description = "리뷰 목록 조회 응답 DTO")
@Getter
public class ReviewListResponseDto {

    @Schema(description = "리뷰 ID", example = "1")
    private final Long id;

    @Schema(description = "주문 번호", example = "ORD-20250701-000001")
    private final String orderNumber;

    @Schema(description = "고객명", example = "홍길동")
    private final String customerName;

    @Schema(description = "상품명", example = "무선 마우스")
    private final String itemName;

    @Schema(description = "평점", example = "5")
    private final Integer rating;

    @Schema(description = "리뷰 내용", example = "배송이 빠르고 상품 품질이 매우 만족스럽습니다.")
    private final String content;

    @Schema(description = "리뷰 작성일시", example = "2025-01-15T14:30:00")
    private final LocalDateTime createdAt;

    public ReviewListResponseDto(Review review) {
        this.id = review.getId();
        this.orderNumber = review.getOrder().getOrderNumber();
        this.customerName = review.getOrder().getCustomer().getName();
        this.itemName = review.getOrder().getItem().getName();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}