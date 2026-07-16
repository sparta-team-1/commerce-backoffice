package org.example.commercebackoffice.customer.dto;

import lombok.Getter;

@Getter
public class PutCustomerResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;

    public PutCustomerResponse(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
