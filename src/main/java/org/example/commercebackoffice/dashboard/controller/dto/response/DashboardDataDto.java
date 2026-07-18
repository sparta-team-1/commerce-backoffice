package org.example.commercebackoffice.dashboard.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.commercebackoffice.admin.controller.dto.AdminInfoForDashboard;
import org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard;
import org.example.commercebackoffice.item.dto.response.ItemInfoForDashboard;
import org.example.commercebackoffice.order.domain.dto.OrderDetailResponseDto;
import org.example.commercebackoffice.order.domain.dto.OrderInfoForDashboard;
import org.example.commercebackoffice.review.domain.dto.ReviewInfoForDashboard;

import java.util.List;

@Schema(description = "대시보드 데이터 응답")
public record DashboardDataDto(

        @Schema(description = "대시보드 요약 정보")
        DashboardSummaryResponse summary,

        @Schema(description = "위젯 정보")
        WidgetResponse widget,

        @Schema(description = "차트 데이터")
        ChartDataResponse charts,

        @Schema(description = "최근 주문 목록")
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