package org.example.commercebackoffice.customer.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;
import org.example.commercebackoffice.customer.domain.Customer;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.example.commercebackoffice.customer.dto.*;
import org.example.commercebackoffice.customer.repository.CustomerRepository;
import org.example.commercebackoffice.order.repository.OrderRepository; // 고객 조회 데이터 확장
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository; // OrderRepository 주입(고객 조회 데이터 확장)
    // 고객 리스트 조회
    @Transactional(readOnly = true)
    public Page<GetCustomerListResponse> getCustomers(
            String keyword,
            CustomerStatus status,
            Pageable pageable
    ) {
        Page<Customer> customers;

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

        /* return customers.map(customer -> new GetCustomerListResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getCreatedAt()
                )
            ); */ // 고객 조회 데이터 확장 기능을 위해서 주석처리

        // 단순 맵핑-> DB를 조회해 통계값을 같이 넘겨주도록 변경
        return customers.map(customer -> {
            Long totalOrderCount = orderRepository.countByCustomerId(customer.getId());
            Long totalPurchaseAmount = orderRepository.sumTotalPriceByCustomerId(customer.getId());

            return new GetCustomerListResponse(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getStatus(),
                    customer.getCreatedAt(),
                    totalOrderCount,        // 고객 조회 데이터 확장
                    totalPurchaseAmount    //  고객 조회 데이터 확장
            );
        });

        }



    // 고객 상세 조회
    @Transactional(readOnly = true)
    public GetCustomerDetailResponse getOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND)
        );

        Long totalOrderCount = orderRepository.countByCustomerId(customerId); // 고객 조회 데이터 확장
        Long totalPurchaseAmount = orderRepository.sumTotalPriceByCustomerId(customerId); // 고객 조회 데이터 확장

        return new GetCustomerDetailResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getCreatedAt(),
                totalOrderCount, // 고객 조회 데이터 확장
                totalPurchaseAmount // 고객 조회 데이터 확장
        );
    }



    // 고객 정보 수정
    @Transactional
    public PutCustomerResponse updateCustomer(Long customerId, PutCustomerRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
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
        Customer customer = customerRepository.findById(customerId).orElseThrow(
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
