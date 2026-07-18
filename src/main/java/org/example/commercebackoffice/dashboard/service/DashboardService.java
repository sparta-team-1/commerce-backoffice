package org.example.commercebackoffice.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.dto.AdminInfoForDashboard;
import org.example.commercebackoffice.admin.service.AdminService;
import org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard;
import org.example.commercebackoffice.customer.service.CustomerService;
import org.example.commercebackoffice.dashboard.controller.dto.response.DashboardDataDto;
import org.example.commercebackoffice.item.dto.response.ItemInfoForDashboard;
import org.example.commercebackoffice.item.service.ItemService;
import org.example.commercebackoffice.order.domain.dto.OrderDetailResponseDto;
import org.example.commercebackoffice.order.domain.dto.OrderInfoForDashboard;
import org.example.commercebackoffice.order.service.OrderService;
import org.example.commercebackoffice.review.domain.dto.ReviewInfoForDashboard;
import org.example.commercebackoffice.review.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    public DashboardDataDto getDashboardData() {
        CustomerInfoForDashboard customerInfo = customerService.getCustomerInfoForDashboard();
        AdminInfoForDashboard adminInfo = adminService.getAdminInfoForDashboard();
        ItemInfoForDashboard itemInfo = itemService.getItemInfoForDashboard();
        OrderInfoForDashboard orderInfo = orderService.getOrderInfoForDashboard();
        ReviewInfoForDashboard reviewInfo = reviewService.getReviewInfoForDashboard();
        List<OrderDetailResponseDto> recentOrders = orderService.getOrderInfoForDashboard().recentTenOrders();

        return DashboardDataDto.of(
                adminInfo,
                customerInfo,
                itemInfo,
                orderInfo,
                reviewInfo
        );
    }
}
