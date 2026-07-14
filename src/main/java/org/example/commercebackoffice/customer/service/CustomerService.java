package org.example.commercebackoffice.customer.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.example.commercebackoffice.customer.dto.*;
import org.example.commercebackoffice.customer.entity.CustomerEntity;
import org.example.commercebackoffice.customer.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // 고객 리스트 조회
    @Transactional(readOnly = true)
    public Page<GetCustomerListResponse> getCustomers(
            String keyword,
            CustomerStatus status,
            Pageable pageable
    ) {
        Page<CustomerEntity> customers;

        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasStatus = status != null;

        if (hasKeyword && hasStatus) {
            customers = customerRepository.findByNameContainingAndStatusOrEmailContainingAndStatus(
                    keyword,
                    status,
                    keyword,
                    status,
                    pageable
            );

        } else if (hasKeyword) {
            customers = customerRepository.findByNameContainingOrEmailContaining(
                    keyword,
                    keyword,
                    pageable);

        } else if (hasStatus) {
            customers = customerRepository.findByStatus(status, pageable);

        } else {
            customers = customerRepository.findAll(pageable);
        }

        return customers.map(customer -> new GetCustomerListResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getCreatedAt()
                )
            );

        }



    // 고객 상세 조회
    @Transactional(readOnly = true)
    public GetCustomerDetailResponse getOne(Long customerId) {
        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
        return new GetCustomerDetailResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getCreatedAt()
        );
    }



    // 고객 정보 수정
    @Transactional
    public PutCustomerResponse updateCustomer(Long customerId, PutCustomerRequest request) {
        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND)
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
                () -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND)
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
            throw new CustomException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        customerRepository.deleteById(customerId);
    }
}
