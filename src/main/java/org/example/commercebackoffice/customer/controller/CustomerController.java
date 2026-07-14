package org.example.commercebackoffice.customer.controller;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.customer.dto.*;
import org.example.commercebackoffice.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객 리스트 조회
    @GetMapping("/customers")
    public


    // 고객 상세 조회
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<GetCustomerDetailResponse> getOne(@PathVariable Long customerId) {
        GetCustomerDetailResponse result = customerService.getOne(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    // 고객 정보 수정
    @PutMapping("/customers/{customerId}")
    public ResponseEntity<PutCustomerResponse> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody PutCustomerRequest request
            ) {
        PutCustomerResponse result = customerService.updateCustomer(customerId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    // 고객 상태 변경
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<PatchCustomerResponse> updateStatus(
            @PathVariable Long customerId,
            @RequestBody PatchCustomerRequest patchCustomerRequest
    ) {
        PatchCustomerResponse result = customerService.updateStatus(customerId, patchCustomerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    // 고객 삭제
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
