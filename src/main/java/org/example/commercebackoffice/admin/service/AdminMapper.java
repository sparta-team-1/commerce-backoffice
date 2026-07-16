package org.example.commercebackoffice.admin.service;

import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminDetailResponse;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminResponse;
import org.example.commercebackoffice.admin.controller.auth.SessionUser;
import org.example.commercebackoffice.admin.domain.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public static AdminResponse toAdminResponse(Admin admin) {
        return new AdminResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getApprovedAt()
        );
    }

    public static SessionUser toSessionUser(Admin admin) {
        return new SessionUser(
                admin.getId(),
                admin.getEmail(),
                admin.getRole()
        );
    }

    public static AdminDetailResponse toAdminDetailResponse(Admin admin) {
        return new AdminDetailResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getRejectionReason(),
                admin.getCreatedAt(),
                admin.getApprovedAt(),
                admin.getRejectedAt()
        );
    }
}
