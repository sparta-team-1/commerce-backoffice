package org.example.commercebackoffice.customer.dto;

public record CustomerInfoForDashboard(
        Long totalCustomerCount,
        Long activeCustomerCount,
        Long inactiveCustomerCount,
        Long suspendedCustomerCount
) {
}
