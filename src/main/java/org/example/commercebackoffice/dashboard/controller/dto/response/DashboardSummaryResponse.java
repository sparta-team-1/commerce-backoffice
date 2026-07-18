package org.example.commercebackoffice.dashboard.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.commercebackoffice.admin.controller.dto.AdminInfoForDashboard;
import org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard;
import org.example.commercebackoffice.item.dto.response.ItemInfoForDashboard;
import org.example.commercebackoffice.order.domain.dto.OrderInfoForDashboard;
import org.example.commercebackoffice.review.domain.dto.ReviewInfoForDashboard;

public record DashboardSummaryResponse(
        Long totalAdminCount,
        Long activeAdminCount,
        Long totalCustomerCount,
        Long activeCustomerCount,
        Long totalItemCount,
        Long stockInsufficientCount,
        Long totalOrderCount,
        Long todayOrderCount,
        Long totalReviewCount,
        //DTO -> JSON으로 직렬화 시 소수점 한 자리 수만 반환하도록
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.0")
        Double avgReviewRating
) {
    public static DashboardSummaryResponse from(AdminInfoForDashboard adminInfo,
                                                CustomerInfoForDashboard customerInfo,
                                                ItemInfoForDashboard itemInfo,
                                                OrderInfoForDashboard orderInfo,
                                                ReviewInfoForDashboard reviewInfo) {
        return new DashboardSummaryResponse(
                adminInfo.totalAdminCount(),
                adminInfo.activeAdminCount(),
                customerInfo.totalCustomerCount(),
                customerInfo.activeCustomerCount(),
                itemInfo.totalItemCount(),
                itemInfo.insufficientItemCount(),
                orderInfo.orderSummaryDto().totalOrderCount(),
                orderInfo.orderSummaryDto().todayOrderCount(),
                reviewInfo.totalReviewCount(),
                reviewInfo.totalReviewAvg()
        );
    }
}
