package org.example.commercebackoffice.dashboard.controller.dto.response;

public record WidgetResponse(
        Long totalRevenue,
        Long todayRevenue,
        Long preparingOrderCount,
        Long shippingOrderCount,
        Long deliveredOrderCount,
        Long stockInsufficientItemCount,
        Long outOfStockItemCount
) {
}
