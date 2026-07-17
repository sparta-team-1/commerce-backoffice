package org.example.commercebackoffice.dashboard.controller.dto.response;

import org.example.commercebackoffice.item.domain.enums.ItemStatus;

public record ChartDataResponse(
        ReviewRating reviewRating,
        ItemStatus status,
        ProductCategory productCategory
) {
}
