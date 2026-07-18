package org.example.commercebackoffice.dashboard.controller.dto.response;

import org.example.commercebackoffice.admin.controller.dto.AdminInfoForDashboard;
import org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard;
import org.example.commercebackoffice.item.dto.response.ItemInfoForDashboard;
import org.example.commercebackoffice.order.domain.dto.OrderDetailResponseDto;
import org.example.commercebackoffice.order.domain.dto.OrderInfoForDashboard;
import org.example.commercebackoffice.review.domain.dto.ReviewInfoForDashboard;

import java.util.List;

public record DashboardDataDto(
        DashboardSummaryResponse summary,
        WidgetResponse widget,
        ChartDataResponse charts,
        List<OrderDetailResponseDto> recentOrders
) {
    public static DashboardDataDto of(
            AdminInfoForDashboard adminInfo,
            CustomerInfoForDashboard customerInfo,
            ItemInfoForDashboard itemInfo,
            OrderInfoForDashboard orderInfo,
            ReviewInfoForDashboard reviewInfo
    ) {
        return new DashboardDataDto(
                DashboardSummaryResponse.from(
                        adminInfo,
                        customerInfo,
                        itemInfo,
                        orderInfo,
                        reviewInfo
                ),
                WidgetResponse.from(
                        orderInfo,
                        itemInfo
                ),
                ChartDataResponse.from(
                        reviewInfo,
                        customerInfo,
                        itemInfo
                ),
                orderInfo.recentTenOrders()
        );
    }
}
