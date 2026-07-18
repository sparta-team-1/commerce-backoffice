package org.example.commercebackoffice.review.domain.dto;

import java.util.Map;

public record ReviewStatsDto(double averageRating, long totalCount, Map<Integer, Long> ratingCountMap) {
}