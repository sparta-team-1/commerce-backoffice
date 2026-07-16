package org.example.commercebackoffice.customer.repository;

import org.example.commercebackoffice.customer.domain.Customer;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

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
}
