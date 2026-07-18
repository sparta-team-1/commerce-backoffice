package org.example.commercebackoffice.order.repository;

import org.example.commercebackoffice.order.domain.Order;
import org.example.commercebackoffice.order.domain.dto.OrderSummaryDto;
import org.example.commercebackoffice.order.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Long countByCustomerId(Long customerId);// 특정 고객의 총 주문 수 계산 (고객 조회 데이터 확장)
    // 특정 고객의 총 구매 금액 계산 (null 방지,고객 조회 데이터 확장)
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.customer.id = :customerId")
    Long sumTotalPriceByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT o FROM Order o " +
            "WHERE (:status IS NULL OR o.status = :status) " +
            "AND (:keyword IS NULL " +
            "     OR o.orderNumber LIKE %:keyword% " +
            "     OR o.customer.name LIKE %:keyword%)")
    Page<Order> search(@Param("status") OrderStatus status,
                       @Param("keyword") String keyword,
                       Pageable pageable);

    @Query("""
        SELECT new org.example.commercebackoffice.order.domain.dto.OrderSummaryDto(
            COUNT(o),
            COUNT(CASE WHEN o.orderedAt >= :startOfToday AND o.orderedAt <= :endOfToday THEN 1 END),
            COUNT(CASE WHEN o.status = 'READY' THEN 1 END),
            COUNT(CASE WHEN o.status = 'SHIPPING' THEN 1 END),
            COUNT(CASE WHEN o.status = 'DELIVERED' THEN 1 END),
            CAST(COALESCE((SUM(o.totalPrice)), 0L) AS Long),
            CAST(COALESCE(SUM(CASE WHEN o.orderedAt >= :startOfToday AND
                o.orderedAt <= :endOfToday THEN o.totalPrice END), 0L) AS Long)
        )
        FROM Order o
    """)
    OrderSummaryDto getOrderSummaryInfo(@Param("startOfToday") LocalDateTime startOfToday,
                                        @Param("endOfToday") LocalDateTime endOfToday);

    @Query("""
        SELECT o 
        FROM Order o
        JOIN FETCH o.item
        JOIN FETCH o.customer
        JOIN FETCH o.admin
        ORDER BY o.orderedAt DESC
    """)
    List<Order> getRecentTenOrders(Pageable pageable);
}
