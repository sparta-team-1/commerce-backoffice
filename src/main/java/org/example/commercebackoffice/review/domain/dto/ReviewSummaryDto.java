package org.example.commercebackoffice.review.domain.dto;

import lombok.Getter;
import org.example.commercebackoffice.review.domain.Review;

import java.time.LocalDateTime;

@Getter
public class ReviewSummaryDto {
    private final String customerName;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;

    public ReviewSummaryDto(Review review) {
        this.customerName = review.getOrder().getCustomer().getName();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}