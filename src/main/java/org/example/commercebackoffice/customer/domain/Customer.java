package org.example.commercebackoffice.customer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commercebackoffice.common.entity.BaseEntity;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

@Entity
@Getter
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
   
}
