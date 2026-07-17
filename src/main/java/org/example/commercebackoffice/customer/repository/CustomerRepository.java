package org.example.commercebackoffice.customer.repository;

import org.example.commercebackoffice.customer.domain.Customer;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByStatusNot(CustomerStatus status, Pageable pageable);

    Page<Customer> findByNameContainingOrEmailContaining(
            String nameKeyword,
            String emailKeyword,
            Pageable pageable
    );

    Page<Customer> findByStatus(
            CustomerStatus status,
            Pageable pageable
    );

    Page<Customer> findByNameContainingAndStatusOrEmailContainingAndStatus(
            String nameKeyword,
            CustomerStatus nameStatus,
            String emailKeyword,
            CustomerStatus emailStatus,
            Pageable pageable
    );

    //전체 고객 수 및 각 상태 별 고객 수 조회
    @Query("""
        SELECT new org.example.commercebackoffice.customer.dto.CustomerInfoForDashboard(
            COUNT(c),
            COUNT(CASE WHEN c.status = 'ACTIVE' THEN 1 END),
            COUNT(CASE WHEN c.status = 'INACTIVE' THEN 1 END),
            COUNT(CASE WHEN c.status = 'SUSPEND' THEN 1 END)
        )
        FROM Customer c
    """)
    CustomerInfoForDashboard countAllCustomersAndCountCustomerByStatus();
}
