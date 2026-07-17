package org.example.commercebackoffice.order.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.example.commercebackoffice.order.domain.enums.OrderStatus;

@Schema(description = "주문 검색 조건 DTO")
@Getter
@Setter
public class OrderSearchCondition {

    @Schema(description = "검색 키워드(주문번호, 고객명 등)", example = "홍길동")
    private String keyword;

    @Schema(description = "페이지 번호", example = "1", defaultValue = "1")
    private int page = 1;

    @Schema(description = "페이지 크기", example = "10", defaultValue = "10")
    private int size = 10;

    @Schema(description = "정렬 기준", example = "orderedAt", defaultValue = "orderedAt")
    private String sort = "orderedAt";

    @Schema(description = "정렬 방향", example = "desc", allowableValues = {"asc", "desc"}, defaultValue = "desc")
    private String order = "desc";

    @Schema(description = "주문 상태", example = "COMPLETED")
    private OrderStatus status;
}