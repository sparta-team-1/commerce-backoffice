package org.example.commercebackoffice.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PageResponseDto<ReviewListResponseDto>> getReviews(@ModelAttribute ReviewSearchCondition condition) {
        return ResponseEntity.ok(reviewService.getReviews(condition));
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewCreateRequestDto requestDto) {
        ReviewResponseDto response = reviewService.createReview(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //리뷰 상세 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> getReviewDetail(
            @PathVariable Long reviewId
    ) {
      ReviewDetailResponseDto response   = reviewService.getReviewDetail(reviewId);

      return ResponseEntity.ok(response);
    }
    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview (
            @PathVariable Long reviewId
    ) {
        //삭제할 리뷰 ID를 서비스에 전달
        reviewService.deleteReview(reviewId);

        return ResponseEntity.noContent().build();
        //삭제 후 클라이언트에게 돌려줄 데이터가 없기 때문에 응답 본문을 비워둔다.
    }
}