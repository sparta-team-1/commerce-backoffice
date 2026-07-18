package org.example.commercebackoffice.item.dto.response;

import java.util.Map;

public record ItemCountInfo(
        Long totalItemCount,
        Long insufficientItemCount,
        Long outOfStockItemCount
) {
}
