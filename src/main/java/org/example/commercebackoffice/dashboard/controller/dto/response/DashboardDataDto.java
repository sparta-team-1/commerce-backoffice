package org.example.commercebackoffice.dashboard.controller.dto.response;

import org.example.commercebackoffice.order.domain.dto.OrderDetailResponseDto;

import java.util.List;

public record DashboardDataDto(
        DashboardSummaryResponse summary,
        WidgetResponse widget,
        ChartDataResponse charts,
        List<OrderDetailResponseDto> recentOrders
) {
}
