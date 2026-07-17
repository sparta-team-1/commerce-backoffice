package org.example.commercebackoffice.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.order.domain.Order;
import org.example.commercebackoffice.order.domain.enums.OrderStatus;

import java.time.LocalDateTime;

@Schema(description = "주문 응답 DTO")
@Getter
public class OrderResponseDto {

    @Schema(description = "주문 ID", example = "1")
    private final Long id;

    @Schema(description = "주문 번호", example = "ORD-20250701-000001")
    private final String orderNumber;

    @Schema(description = "고객명", example = "홍길동")
    private final String customerName;

    @Schema(description = "상품명", example = "무선 마우스")
    private final String itemName;

    @Schema(description = "주문 수량", example = "2")
    private final Integer quantity;

    @Schema(description = "총 주문 금액", example = "70000")
    private final Long totalPrice;

    @Schema(description = "주문 일시", example = "2025-01-15T14:30:00")
    private final LocalDateTime orderedAt;

    @Schema(description = "주문 상태", example = "COMPLETED")
    private final OrderStatus status;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.customerName = order.getCustomer().getName();
        this.itemName = order.getItem().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = order.getOrderedAt();
        this.status = order.getStatus();
    }
}