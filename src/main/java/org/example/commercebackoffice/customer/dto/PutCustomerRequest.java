package org.example.commercebackoffice.customer.dto;

import lombok.Getter;

@Getter
public class PutCustomerRequest {

    private String name;
    private String email;
    private String phone;
}
