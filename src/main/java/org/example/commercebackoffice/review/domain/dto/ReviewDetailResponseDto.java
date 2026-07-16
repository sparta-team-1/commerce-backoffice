package org.example.commercebackoffice.review.domain.dto;

import lombok.Getter;
import org.example.commercebackoffice.review.domain.Review;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

@Getter
public class ReviewDetailResponseDto {
    private final String itemName; //상품이름

    private final String customerName; //리뷰를 작성한 고객 이름

    private final String customerEmail;  //리뷰를 작성한 고객 이메일

    private final LocalDateTime createdAt; //리뷰 작성일

    private final Integer rating; //리뷰 평점

    private final String content; //리뷰 내용

    //Review 엔티티를 상세 조회 응답 DTO로 바꾸는 생성
    public  ReviewDetailResponseDto(Review review) {
        this.itemName = review.getOrder()
                                .getItem()
                                 .getName();

        this.customerName = review.getOrder()
                                    .getCustomer()
                                    .getName(); //Review ->Order ->Costomer ->고객명

        this.customerEmail = review.getOrder()
                                     .getCustomer()
                                     .getEmail();  // Review → Order → Customer → 고객 이메일
        this.createdAt = review.getCreatedAt();
        this.rating = review.getRating(); //Review 엔티티의 평점을 가져옴
        this.content = review.getContent(); //Review 엔티티의 리뷰 내용을 가져옴
    }
}
