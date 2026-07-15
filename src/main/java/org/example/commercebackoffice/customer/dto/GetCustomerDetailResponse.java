package org.example.commercebackoffice.customer.dto;

import lombok.Getter;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

import java.time.LocalDateTime;

@Getter
public class GetCustomerDetailResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    private final LocalDateTime createdAt;

    public GetCustomerDetailResponse(
            Long id,
            String name,
            String email,
            String phone,
            CustomerStatus status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
    }
}
