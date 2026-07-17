package org.example.commercebackoffice.dashboard.controller.dto.response;

public record DashboardSummaryResponse(
        int totalAdminCount,
        int activeAdminCount,
        int totalCustomerCount,
        int activeCustomerCount,
        Long totalItemCount,
        Long stockInsufficientCount,
        Long totalOrderCount,
        Long todayOrderCount,
        Long totalReviewCount,
        Long avgReviewRating
) {
}
