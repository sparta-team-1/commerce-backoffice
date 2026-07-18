package org.example.commercebackoffice.item.dto.response;

import java.util.Map;

public record ItemInfoForDashboard(
        Long totalItemCount,
        Long insufficientItemCount,
        Long outOfStockItemCount,
        Map<String, Long> category
) {
    public static ItemInfoForDashboard from(ItemCountInfo countInfo, Map<String, Long> categoryInfo) {
        return new ItemInfoForDashboard(
            countInfo.totalItemCount(),
            countInfo.insufficientItemCount(),
            countInfo.outOfStockItemCount(),
            categoryInfo
        );
}
}
