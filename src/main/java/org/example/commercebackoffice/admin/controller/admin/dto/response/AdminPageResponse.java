package org.example.commercebackoffice.admin.controller.admin.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record AdminPageResponse (
        List<AdminResponse> content,
        int page,
        int size,
        long total,
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
