package org.example.commercebackoffice.order.domain.dto;

import java.util.List;

public record OrderInfoForDashboard(
        OrderSummaryDto orderSummaryDto,
        List<OrderDetailResponseDto> recentTenOrders
) {
}
