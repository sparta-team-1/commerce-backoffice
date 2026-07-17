package org.example.commercebackoffice.item.dto.response;

public record ItemInfoForDashboard(
        Long totalItemCount,
        Long insufficientItemCount,
        Long outOfStockItemCount,
        String category,
        Long categoryCount
) {
}
