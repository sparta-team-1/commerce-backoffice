package org.example.commercebackoffice.customer.controller;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객 리스트 조회


    // 고객 상세 조회


    // 고객 정보 수정


    // 고객 상태 변경


    // 고객 삭제
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
