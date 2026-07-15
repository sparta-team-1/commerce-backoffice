package org.example.commercebackoffice.admin.domain.enums;

import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;

public enum AdminStatus {
    PENDING, ACTIVE, INACTIVE, SUSPENDED, REJECTED;

    public static AdminStatus from(String status) {
        try {
            return valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.UNKNOWN_STATUS, status);
        }
    }
}
