package org.example.commercebackoffice.item.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.item.domain.Item;
import org.example.commercebackoffice.review.domain.dto.ReviewStatsDto;
import org.example.commercebackoffice.review.domain.dto.ReviewSummaryDto;


import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "상품 조회 응답 DTO")
@Getter
public class ItemResponseDto {

    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "관리자 ID", example = "1")
    private Long adminId;

    @Schema(description = "관리자 이름", example = "관리자")
    private String adminName;

    @Schema(description = "관리자 이메일", example = "admin@example.com")
    private String adminEmail;

    @Schema(description = "상품명", example = "무선 마우스")
    private String name;

    @Schema(description = "카테고리", example = "전자기기")
    private String category;

    @Schema(description = "상품 가격", example = "35000")
    private Long price;

    @Schema(description = "재고 수량", example = "100")
    private Integer stock;

    @Schema(description = "상품 상태", example = "ON_SALE")
    private String status;

    @Schema(description = "등록일시", example = "2025-01-15T14:30:00")
    private LocalDateTime createdAt;
    private ReviewStatsDto reviewStats;
    private List<ReviewSummaryDto> latestReviews;
    private String reviewMessage;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        if (item.getAdmin() != null) {
            this.adminId = item.getAdmin().getId();
            this.adminName = item.getAdmin().getName();
            this.adminEmail = item.getAdmin().getEmail();
        }
        this.name = item.getName();
        this.category = item.getCategory();
        this.price = item.getPrice();
        this.stock = item.getStock();
        this.status = item.getStatus().name();
        this.createdAt = item.getCreatedAt();
    }

    public ItemResponseDto(Item item, ReviewStatsDto reviewStats, List<ReviewSummaryDto> latestReviews) {
        this(item);
        if (latestReviews == null || latestReviews.isEmpty()) {
            this.reviewStats = null;
            this.latestReviews = null;
            this.reviewMessage = "해당 상품의 리뷰가 없습니다.";
        } else {
            this.reviewStats = reviewStats;
            this.latestReviews = latestReviews;
            this.reviewMessage = null;
        }
    }
}