package org.example.commercebackoffice.dashboard.controller.dto.response;

import org.example.commercebackoffice.item.dto.response.ItemInfoForDashboard;
import org.example.commercebackoffice.order.domain.dto.OrderInfoForDashboard;

public record WidgetResponse(
        Long totalRevenue,
        Long todayRevenue,
        Long preparingOrderCount,
        Long shippingOrderCount,
        Long deliveredOrderCount,
        Long stockInsufficientItemCount,
        Long outOfStockItemCount
) {
    public static WidgetResponse from(
            OrderInfoForDashboard orderInfo,
            ItemInfoForDashboard itemInfo
    ) {
        return new WidgetResponse(
                orderInfo.orderSummaryDto().totalRevenue(),
                orderInfo.orderSummaryDto().todayRevenue(),
                orderInfo.orderSummaryDto().preparingOrderCount(),
                orderInfo.orderSummaryDto().deliveringOrderCount(),
                orderInfo.orderSummaryDto().completedOrderCount(),
                itemInfo.insufficientItemCount(),
                itemInfo.outOfStockItemCount()
        );
    }
}
