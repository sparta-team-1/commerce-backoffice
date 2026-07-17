package org.example.commercebackoffice.review.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "리뷰 검색 조건 DTO")
@Getter
@Setter
public class ReviewSearchCondition {

    @Schema(description = "검색 키워드(상품명, 고객명, 주문번호 등)", example = "무선 마우스")
    private String keyword;

    @Schema(description = "페이지 번호", example = "1", defaultValue = "1")
    private int page = 1;

    @Schema(description = "페이지 크기", example = "10", defaultValue = "10")
    private int size = 10;

    @Schema(description = "정렬 기준", example = "createdAt", defaultValue = "createdAt")
    private String sort = "createdAt";

    @Schema(description = "정렬 방향", example = "desc", allowableValues = {"asc", "desc"}, defaultValue = "desc")
    private String order = "desc";

    @Schema(description = "평점", example = "5")
    private Integer rating;
}