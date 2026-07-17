package org.example.commercebackoffice.dashboard.controller.dto.response;

import org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard;
import org.example.commercebackoffice.item.dto.response.ItemInfoForDashboard;
import org.example.commercebackoffice.review.domain.dto.ReviewInfoForDashboard;

import java.util.Arrays;
import java.util.List;

public record ChartDataResponse(
        List<ReviewRating> reviewRating,
        List<CustomerStatus> customerStatus,
        List<ProductCategory> productCategory
) {
    public static ChartDataResponse from(
            ReviewInfoForDashboard reviewInfo,
            CustomerInfoForDashboard customerInfo,
            ItemInfoForDashboard itemInfo
    ) {
        return new ChartDataResponse(
                reviewInfo.ratingCount().entrySet().stream()
                        .map(entry -> new ReviewRating(
                                entry.getKey(),
                                entry.getValue()
                        )).toList(),
                Arrays.stream(org.example.commercebackoffice.customer.domain.enums.CustomerStatus.values())
                        .map(status -> new CustomerStatus(
                                status,
                                switch (status) {
                                    case ACTIVE -> customerInfo.activeCustomerCount();
                                    case INACTIVE -> customerInfo.inactiveCustomerCount();
                                    case SUSPENDED ->  customerInfo.suspendedCustomerCount();
                                })
                        ).toList(),
                itemInfo.category().entrySet().stream()
                        .map(entry -> new ProductCategory(
                                entry.getKey(),
                                entry.getValue()
                        )).toList()
        );
    }
}
