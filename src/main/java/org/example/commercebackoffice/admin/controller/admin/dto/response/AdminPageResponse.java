package org.example.commercebackoffice.admin.controller.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "관리자 리스트 조회 DTO")
public record AdminPageResponse (
        @Schema(description = "관리자 정보")
        List<AdminResponse> content,
        @Schema(description = "페이지 번호", example = "0")
        int page,
        @Schema(description = "페이지 크기", example = "10")
        int size,
        @Schema(description = "전체 리스트 크기", example = "1")
        long total,
        @Schema(description = "페이지 갯수", example = "1")
        int totalPages
) {
    public static AdminPageResponse from(Page<AdminResponse> page) {
        return new AdminPageResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
