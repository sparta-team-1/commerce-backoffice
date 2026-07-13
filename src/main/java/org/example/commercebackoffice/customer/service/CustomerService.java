package org.example.commercebackoffice.customer.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // 고객 리스트 조회
    @Transactional


    // 고객 상세 조회
    @Transactional


    // 고객 정보 수정
    @Transactional


    // 고객 상태 변경
    @Transactional


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
