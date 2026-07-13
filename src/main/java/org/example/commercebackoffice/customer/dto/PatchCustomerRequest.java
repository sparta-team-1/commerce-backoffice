package org.example.commercebackoffice.customer.dto;

import lombok.Getter;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

@Getter
public class PatchCustomerRequest {

    private CustomerStatus status;
}
