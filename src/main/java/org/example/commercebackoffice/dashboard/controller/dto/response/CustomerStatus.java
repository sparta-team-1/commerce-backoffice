package org.example.commercebackoffice.dashboard.controller.dto.response;

public record CustomerStatus(
        org.example.commercebackoffice.customer.domain.enums.CustomerStatus status,
        Long count
) {
}
