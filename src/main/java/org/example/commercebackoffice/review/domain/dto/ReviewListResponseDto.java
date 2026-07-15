package org.example.commercebackoffice.review.domain.dto;

import lombok.Getter;
import org.example.commercebackoffice.review.domain.Review;

import java.time.LocalDateTime;

@Getter
public class ReviewListResponseDto {

    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String itemName;
    private final Integer rating;
    private final String content;
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