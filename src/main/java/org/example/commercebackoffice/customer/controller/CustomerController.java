package org.example.commercebackoffice.customer.controller;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.example.commercebackoffice.customer.dto.*;
import org.example.commercebackoffice.customer.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객 리스트 조회
    @GetMapping("/api/customers")
    public ResponseEntity<ApiResponse<Page<GetCustomerListResponse>>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(required = false) CustomerStatus status
    ) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<GetCustomerListResponse> result =
                customerService.getCustomers(keyword, status, pageable);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_LIST_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.CUSTOMER_LIST_SELECT_SUCCESS, result));
    }


    // 고객 상세 조회
    @GetMapping("/api/customers/{customerId}")
    public ResponseEntity<ApiResponse<GetCustomerDetailResponse>> getOne(@PathVariable Long customerId) {
        GetCustomerDetailResponse result = customerService.getOne(customerId);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_DETAIL_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.CUSTOMER_DETAIL_SELECT_SUCCESS, result));
    }


    // 고객 정보 수정
    @PutMapping("/api/customers/{customerId}")
    public ResponseEntity<ApiResponse<PutCustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody PutCustomerRequest request
    ) {
        PutCustomerResponse result = customerService.updateCustomer(customerId, request);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.CUSTOMER_INFO_UPDATE_SUCCESS, result));
    }


    // 고객 상태 변경
    @PatchMapping("/api/customers/{customerId}")
    public ResponseEntity<ApiResponse<PatchCustomerResponse>> updateStatus(
            @PathVariable Long customerId,
            @RequestBody PatchCustomerRequest patchCustomerRequest
    ) {
        PatchCustomerResponse result = customerService.updateStatus(customerId, patchCustomerRequest);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_STATUS_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.CUSTOMER_STATUS_UPDATE_SUCCESS, result));
    }


    // 고객 삭제
    @DeleteMapping("/api/customers/{customerId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_DELETE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.CUSTOMER_DELETE_SUCCESS, null));
    }
}
