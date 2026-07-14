package org.example.commercebackoffice.customer.repository;

import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.example.commercebackoffice.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Page<CustomerEntity> findByNameContainingOrEmailContaining(
            String nameKeyword,
            String emailKeyword,
            Pageable pageable
    );

    Page<CustomerEntity> findByStatus(
            CustomerStatus status,
            Pageable pageable
    );

    Page<CustomerEntity> findByNameContainingAndStatusOrEmailContainingAndStatus(
            String nameKeyword,
            CustomerStatus nameStatus,
            String emailKeyword,
            CustomerStatus emailStatus,
            Pageable pageable
    );
}
