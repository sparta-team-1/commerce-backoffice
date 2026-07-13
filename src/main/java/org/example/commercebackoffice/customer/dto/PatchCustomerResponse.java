package org.example.commercebackoffice.customer.dto;

import lombok.Getter;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;

@Getter
public class PatchCustomerResponse {

    private final Long id;
    private final CustomerStatus status;

    public PatchCustomerResponse(Long id, CustomerStatus status) {
        this.id = id;
        this.status = status;
    }
}
