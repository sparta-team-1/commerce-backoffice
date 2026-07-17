package org.example.commercebackoffice.review.domain.dto;

import java.util.Map;

public record ReviewInfoForDashboard(
        Double totalReviewAvg,
        Long totalReviewCount,
        Map<Integer, Long> ratingCount
) {
    public static ReviewInfoForDashboard from(ReviewTotalAvgAndCount reviewTotalAvgAndCount, Map<Integer, Long> ratingCount) {
        return new ReviewInfoForDashboard(
                reviewTotalAvgAndCount.totalAvg(),
                reviewTotalAvgAndCount.totalCount(),
                ratingCount
        );
    }
}
