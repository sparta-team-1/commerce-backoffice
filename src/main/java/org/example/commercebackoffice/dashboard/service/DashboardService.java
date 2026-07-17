package org.example.commercebackoffice.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.service.AdminService;
import org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard;
import org.example.commercebackoffice.customer.service.CustomerService;
import org.example.commercebackoffice.item.service.ItemService;
import org.example.commercebackoffice.order.service.OrderService;
import org.example.commercebackoffice.review.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    public void getSummary() {
        CustomerInfoForDashboard customerInfo = customerService.getCustomerInfoForDashboard();
    }
}
