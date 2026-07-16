package org.example.commercebackoffice.review;

public class ReviewNotFoundException extends RuntimeException{

public ReviewNotFoundException(Long ReviewId) {
    super("리뷰를 찾을 수 없습니다. orderId=" + ReviewId);
}
}
