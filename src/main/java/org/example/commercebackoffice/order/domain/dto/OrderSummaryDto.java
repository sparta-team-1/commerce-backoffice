package org.example.commercebackoffice.order.domain.dto;

public record OrderSummaryDto(
        Long totalOrderCount,
        Long todayOrderCount,
        Long preparingOrderCount,
        Long deliveringOrderCount,
        Long completedOrderCount,
        Long totalRevenue,
        Long todayRevenue
) {
}
