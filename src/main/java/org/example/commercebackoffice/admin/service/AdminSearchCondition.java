package org.example.commercebackoffice.admin.service;

import jakarta.annotation.Nullable;

public record AdminSearchCondition(
        int page,
        int limit,
        String sortBy,
        String sortOrder,
        @Nullable
        String status,
        @Nullable
        String role,
        @Nullable
        String search
) {
}
