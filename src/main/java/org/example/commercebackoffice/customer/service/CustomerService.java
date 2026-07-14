package org.example.commercebackoffice.customer.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.customer.dto.PutCustomerRequest;
import org.example.commercebackoffice.customer.dto.PutCustomerResponse;
import org.example.commercebackoffice.customer.entity.CustomerEntity;
import org.example.commercebackoffice.customer.dto.PatchCustomerRequest;
import org.example.commercebackoffice.customer.dto.PatchCustomerResponse;
import org.example.commercebackoffice.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // 고객 리스트 조회



    // 고객 상세 조회



    // 고객 정보 수정
    @Transactional
    public PutCustomerResponse updateCustomer(Long customerId, PutCustomerRequest request) {
        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 고객입니다.")
        );
        customer.updateCustomer(
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );
        return new PutCustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }



    // 고객 상태 변경
    @Transactional
    public PatchCustomerResponse updateStatus(Long customerId, PatchCustomerRequest request) {
        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 고객입니다.")
        );
        customer.updateStatus(
                request.getStatus()
        );
        return new PatchCustomerResponse(
                customer.getId(),
                customer.getStatus()
        );
    }


    // 고객 삭제
    @Transactional
    public void delete(Long customerId) {
        boolean existence = customerRepository.existsById(customerId);
        if (!existence) {
            throw new IllegalStateException("존재하지 않는 고객입니다.");
        }
        customerRepository.deleteById(customerId);
    }
}
