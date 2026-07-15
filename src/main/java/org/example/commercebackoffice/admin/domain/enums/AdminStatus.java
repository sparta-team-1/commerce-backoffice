package org.example.commercebackoffice.admin.domain.enums;

public enum AdminStatus {
    PENDING, ACTIVE, INACTIVE, SUSPENDED, REJECTED;

    public static AdminStatus from(String status) {
        try {
            return valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unknown status: " + status);
        }
    }
}
