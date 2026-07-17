package org.example.commercebackoffice.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.order.domain.Order;
import org.example.commercebackoffice.order.domain.enums.OrderStatus;

import java.time.LocalDateTime;

@Schema(description = "주문 상세 조회 응답 DTO")
@Getter
public class OrderDetailResponseDto {

    @Schema(description = "주문 번호", example = "ORD-20250701-000001")
    private final String orderNumber;

    @Schema(description = "고객명", example = "홍길동")
    private final String customerName;

    @Schema(description = "고객 이메일", example = "hong@example.com")
    private final String customerEmail;

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

    @Schema(description = "처리 관리자 이름", example = "관리자")
    private final String adminName;

    @Schema(description = "처리 관리자 이메일", example = "admin@example.com")
    private final String adminEmail;

    @Schema(description = "처리 관리자 권한", example = "MASTER")
    private final String adminRole;

    public OrderDetailResponseDto(Order order) {
        this.orderNumber = order.getOrderNumber();
        this.customerName = order.getCustomer().getName();
        this.customerEmail = order.getCustomer().getEmail();
        this.itemName = order.getItem().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = order.getOrderedAt();
        this.status = order.getStatus();

        Admin admin = order.getAdmin();
        this.adminName = admin != null ? admin.getName() : null;
        this.adminEmail = admin != null ? admin.getEmail() : null;
        this.adminRole = admin != null ? admin.getRole().name() : null;
    }

    public static OrderDetailResponseDto from(Order order) {
        return new OrderDetailResponseDto(order);
    }
}