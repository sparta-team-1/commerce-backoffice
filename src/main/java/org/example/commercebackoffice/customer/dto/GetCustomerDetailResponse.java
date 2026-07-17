package org.example.commercebackoffice.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

import java.time.LocalDateTime;

@Schema(description = "고객 상세 정보 DTO")
@Getter
public class GetCustomerDetailResponse {

    @Schema(description = "고객 ID", example = "1")
    private final Long id;

    @Schema(description = "고객명", example = "홍길동")
    private final String name;

    @Schema(description = "이메일", example = "hong@example.com")
    private final String email;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private final String phone;

    @Schema(
            description = "고객 상태",
            example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"}
    )
    private final CustomerStatus status;

    @Schema(
            description = "가입일시",
            example = "2025-01-15T14:30:00"
    )
    private final LocalDateTime createdAt;

    @Schema(
            description = "총 주문 건수",
            example = "25"
    )
    private final Long totalOrderCount;

    @Schema(
            description = "총 구매 금액(원)",
            example = "350000"
    )
    private final Long totalPurchaseAmount;

    public GetCustomerDetailResponse(
            Long id,
            String name,
            String email,
            String phone,
            CustomerStatus status,
            LocalDateTime createdAt,
            Long totalOrderCount,
            Long totalPurchaseAmount
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.totalOrderCount = totalOrderCount;
        this.totalPurchaseAmount = totalPurchaseAmount;
    }
}