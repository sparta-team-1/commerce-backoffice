package org.example.commercebackoffice.customer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = CustomerStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public void updateCustomer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void updateStatus(CustomerStatus status) {
        this.status = status;
    }

    public void changeStatus(CustomerStatus status) {
        this.status = status;
    }
}
