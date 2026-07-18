package org.example.commercebackoffice.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.order.domain.dto.PageResponseDto;
import org.example.commercebackoffice.review.domain.dto.*;
import org.example.commercebackoffice.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping //Get/api/reviews 요청을 이 메서드가 처리한다
    public ResponseEntity<ApiResponse<PageResponseDto<ReviewListResponseDto>>> getReviews(@ModelAttribute ReviewSearchCondition condition) {
        return ResponseEntity.ok(ApiResponse.of(
                SuccessCode.REVIEW_LIST_SELECT_SUCCESS,
                reviewService.getReviews((condition))
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(@Valid @RequestBody ReviewCreateRequestDto requestDto) {
        ReviewResponseDto response = reviewService.createReview(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(
                SuccessCode.REVIEW_CREATE_SUCCESS,
                response
        ));
    }

    //리뷰 상세 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewDetailResponseDto>> getReviewDetail(
            @PathVariable Long reviewId
    ) {
      ReviewDetailResponseDto response  = reviewService.getReviewDetail(reviewId);

      return ResponseEntity.ok(ApiResponse.of(
              SuccessCode.REVIEW_DETAIL_SELECT_SUCCESS,
              response
      ));
    }
    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview (
            @PathVariable Long reviewId
    ) {
        //삭제할 리뷰 ID를 서비스에 전달
        reviewService.deleteReview(reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.of(
                SuccessCode.REVIEW_DELETE_SUCCESS,
                null
        ));
        //삭제 후 클라이언트에게 돌려줄 데이터가 없기 때문에 응답 본문을 비워둔다.
    }
}