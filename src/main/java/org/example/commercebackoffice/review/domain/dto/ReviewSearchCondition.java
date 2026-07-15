package org.example.commercebackoffice.review.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSearchCondition {
    private String keyword;
    private int page = 1;
    private int size = 10;
    private String sort = "createdAt";
    private String order = "desc";
    private Integer rating;
}