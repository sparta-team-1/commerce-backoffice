package org.example.commercebackoffice.common.dto;

import lombok.Getter;
import org.example.commercebackoffice.common.message.SuccessCode;

@Getter
public class ApiResponse<T> {
    private final boolean success; 
    private final String message;  
    private final T data;          

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    private ApiResponse(String message, T data) {
        this.success = true; // 성공 응답만 처리하므로 무조건 true
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(SuccessCode successCode, T data) {
        return new ApiResponse<>(successCode.getMessage(), data);
    }
}
