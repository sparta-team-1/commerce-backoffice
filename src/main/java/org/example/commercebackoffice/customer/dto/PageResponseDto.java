package org.example.commercebackoffice.customer.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponseDto<T> {
    private final List<T> content;         // 실제 데이터 목록 (고객 리스트 등)
    private final int pageNumber;          // 현재 페이지 번호
    private final int pageSize;            // 한 페이지당 데이터 개수
    private final long totalElements;      // 전체 데이터 개수
    private final int totalPages;          // 전체 페이지 수

    public PageResponseDto(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber() + 1; // 스프링은 0페이지부터 시작하므로, 프론트를 위해 +1 해줍니다.
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}