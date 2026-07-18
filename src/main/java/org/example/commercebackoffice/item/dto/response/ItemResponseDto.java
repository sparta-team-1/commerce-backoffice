package org.example.commercebackoffice.item.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.example.commercebackoffice.item.domain.Item;
import org.example.commercebackoffice.review.domain.dto.ReviewStatsDto;
import org.example.commercebackoffice.review.domain.dto.ReviewSummaryDto;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ItemResponseDto {
    private Long id;
    private Long adminId;
    private String adminName;
    private String adminEmail;
    private String name;
    private String category;
    private Long price;
    private Integer stock;
    private String status;
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