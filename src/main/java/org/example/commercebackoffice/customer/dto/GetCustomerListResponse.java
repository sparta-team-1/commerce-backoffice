package org.example.commercebackoffice.customer.dto;

import lombok.Getter;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

import java.time.LocalDateTime;

@Getter
public class GetCustomerListResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    private final LocalDateTime createdAt;

    private final Long totalOrderCount; // 고객 조회 데이터 확장
    private final Long totalPurchaseAmount; // 고객 조회 데이터 확장

    public GetCustomerListResponse(
            Long id,
            String name,
            String email,
            String phone,
            CustomerStatus status,
            LocalDateTime createdAt,
            Long totalOrderCount, // 고객 조회 데이터 확장
            Long totalPurchaseAmount // 고객 조회 데이터 확장

    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.totalOrderCount = totalOrderCount;         // 고객 조회 데이터 확장
        this.totalPurchaseAmount = totalPurchaseAmount; // 고객 조회 데이터 확장
    }


}
