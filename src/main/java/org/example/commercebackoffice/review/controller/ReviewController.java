package org.example.commercebackoffice.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.example.commercebackoffice.order.domain.dto.PageResponseDto;
import org.example.commercebackoffice.review.domain.dto.*;
import org.example.commercebackoffice.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리뷰 관리", description = "리뷰 관리 API")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(
            summary = "리뷰 목록 조회",
            description = "검색 조건과 페이징 정보를 이용하여 리뷰 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<PageResponseDto<ReviewListResponseDto>> getReviews(
            @ModelAttribute ReviewSearchCondition condition) {

        return ResponseEntity.ok(reviewService.getReviews(condition));
    }

    @Operation(
            summary = "리뷰 등록",
            description = "주문에 대한 리뷰를 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "리뷰 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "리뷰 등록 요청",
                    required = true
            )
            @Valid @RequestBody ReviewCreateRequestDto requestDto) {

        ReviewResponseDto response = reviewService.createReview(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "리뷰 상세 조회",
            description = "리뷰 ID를 이용하여 리뷰 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponseDto> getReviewDetail(

            @Parameter(description = "리뷰 ID", example = "1", required = true)
            @PathVariable Long reviewId
    ) {
        ReviewDetailResponseDto response = reviewService.getReviewDetail(reviewId);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "리뷰 삭제",
            description = "리뷰를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "리뷰 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(

            @Parameter(description = "리뷰 ID", example = "1", required = true)
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(reviewId);

        return ResponseEntity.noContent().build();
    }
}