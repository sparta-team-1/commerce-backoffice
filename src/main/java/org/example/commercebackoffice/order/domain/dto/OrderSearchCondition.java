package org.example.commercebackoffice.order.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.commercebackoffice.order.domain.enums.OrderStatus;

@Getter
@Setter
public class OrderSearchCondition {
    private String keyword;
    private int page = 1;
    private int size = 10;
    private String sort = "orderedAt";
    private String order = "desc";
    private OrderStatus status;
}
