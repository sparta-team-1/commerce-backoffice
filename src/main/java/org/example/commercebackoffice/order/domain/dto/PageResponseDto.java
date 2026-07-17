package org.example.commercebackoffice.order.domain.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "페이징 응답 DTO")
@Getter
public class PageResponseDto<T> {

    @ArraySchema(schema = @Schema(description = "조회 결과 목록"))
    private final List<T> content;

    @Schema(description = "현재 페이지 번호(1부터 시작)", example = "1")
    private final int page;

    @Schema(description = "페이지 크기", example = "10")
    private final int size;

    @Schema(description = "전체 데이터 수", example = "125")
    private final long totalElements;

    @Schema(description = "전체 페이지 수", example = "13")
    private final int totalPages;

    public PageResponseDto(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}