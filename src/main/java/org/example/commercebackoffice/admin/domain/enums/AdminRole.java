package org.example.commercebackoffice.admin.domain.enums;

import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;

//역할(슈퍼 ,운영,cs) 과 상태(승인대기)를 자바의 enum(열거형)으로 관리하라고 만든 방.
public enum AdminRole {
    SUPER_ADMIN, OPERATION_ADMIN, CS_ADMIN;

    public static AdminRole from(String role) {
        try {
            return valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.UNKNOWN_ROLE, role);
        }
    }
}
