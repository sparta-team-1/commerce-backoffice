package org.example.commercebackoffice.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.order.domain.dto.PageResponseDto;
import org.example.commercebackoffice.review.domain.dto.ReviewCreateRequestDto;
import org.example.commercebackoffice.review.domain.dto.ReviewListResponseDto;
import org.example.commercebackoffice.review.domain.dto.ReviewResponseDto;
import org.example.commercebackoffice.review.domain.dto.ReviewSearchCondition;
import org.example.commercebackoffice.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<PageResponseDto<ReviewListResponseDto>> getReviews(@ModelAttribute ReviewSearchCondition condition) {
        return ResponseEntity.ok(reviewService.getReviews(condition));
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewCreateRequestDto requestDto) {
        ReviewResponseDto response = reviewService.createReview(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}