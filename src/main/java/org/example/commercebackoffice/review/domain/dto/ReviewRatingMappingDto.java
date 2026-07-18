package org.example.commercebackoffice.review.domain.dto;

public record ReviewRatingMappingDto(
        int rating,
        Long ratingCount
) {
}
