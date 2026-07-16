package org.example.commercebackoffice.review.domain.dto;

import lombok.Getter;
import org.example.commercebackoffice.review.domain.Review;

@Getter
public class ReviewResponseDto {

    private final Long id;
    private final Integer rating;
    private final String content;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.content = review.getContent();
    }
}