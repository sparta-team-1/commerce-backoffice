package org.example.commercebackoffice.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.common.entity.BaseEntity;
import org.example.commercebackoffice.customer.domain.Customer;
import org.example.commercebackoffice.item.domain.Item;
import org.example.commercebackoffice.order.domain.enums.OrderStatus;
import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")   // CS 대리 주문이 아니면 null
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.READY;

    @Column(length = 255)
    private String cancelReason;

    public static Order create(Customer customer, Item item, Admin admin, int quantity, long totalPrice) {
        if (quantity < 1) {
            throw new CustomException(ErrorCode.INVALID_ORDER_QUANTITY);
        }

        Order order = new Order();
        order.customer = customer;
        order.item = item;
        order.admin = admin; // CS 대리 주문이 아니면 null이 그대로 들어옴
        order.quantity = quantity;
        order.totalPrice = totalPrice;
        
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.orderNumber = "ORD-" + date + "-" + random;

        order.orderedAt = LocalDateTime.now();
        order.status = OrderStatus.READY;
        return order;
    }

    public void updateStatus(OrderStatus newStatus) {
        if (!this.status.canTransitionTo(newStatus)) {
            throw new CustomException(ErrorCode.INVALID_ORDER_STATUS_TRANSITION);
        }
        this.status = newStatus;
    }

    public void cancel(String cancelReason) {
        if (this.status != OrderStatus.READY) {
            throw new CustomException(ErrorCode.ORDER_CANCEL_NOT_ALLOWED);
        }
        this.status = OrderStatus.CANCELLED;
        this.cancelReason = cancelReason;
    }
}
