package org.example.commercebackoffice.review.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;
import org.example.commercebackoffice.order.domain.Order;
import org.example.commercebackoffice.order.domain.dto.PageResponseDto;
import org.example.commercebackoffice.order.repository.OrderRepository;
import org.example.commercebackoffice.review.domain.Review;
import org.example.commercebackoffice.review.domain.dto.*;
import org.example.commercebackoffice.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewCreateRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (reviewRepository.existsByOrderId(requestDto.getOrderId())) {
            throw new CustomException(ErrorCode.ALREADY_REVIEWED_ORDER);
        }

        Review review = Review.create(order, requestDto.getRating(), requestDto.getContent());
        Review saved = reviewRepository.save(review);

        return new ReviewResponseDto(saved);
    }

    @Transactional
    public PageResponseDto<ReviewListResponseDto> getReviews(ReviewSearchCondition condition) {
        Sort.Direction direction = "asc".equalsIgnoreCase(condition.getOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(condition.getPage() - 1, condition.getSize(), Sort.by(direction, condition.getSort()));

        Page<Review> reviews = reviewRepository.search(condition.getRating(), condition.getKeyword(), pageable);
        return new PageResponseDto<>(reviews.map(ReviewListResponseDto::new));
    }
    
    
    @Transactional(readOnly = true)
    public ReviewDetailResponseDto getReviewDetail(Long reviewId) {
        Review review = reviewRepository.findDetailById(reviewId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        //조회한 Review 엔티티를 상세 응답 DTO로 변환하여 Controller에 반환
        return  new ReviewDetailResponseDto(review);
    }
    //리뷰 삭제 기능
    @Transactional
    public void deleteReview(Long reviewId) { //Long 은 숫자 객체의 참조값이 전달
        //reviewId를 이용해 데이터베이스에서 리뷰를 찾는다.
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));


        //찾은 Review 객체를 리포지토리에 전달해 삭제
        //트렌잭션이 정상적으로 끝나면
        //데이터베이스에서 DELETE SQL이 실행된다
        reviewRepository.delete(review);
    }

    //대시보드용 리뷰 정보 조회
    @Transactional(readOnly = true)
    public ReviewInfoForDashboard getReviewInfoForDashboard() {
        //리뷰 점수 당 개수 Map으로 반환
        Map<Integer, Long> reviewRatingCount = reviewRepository.getReviewRatingCount().stream()
                .collect(Collectors.toMap(ReviewRatingMappingDto::rating, ReviewRatingMappingDto::ratingCount));

        //리뷰 평균 및 전체 개수
        ReviewTotalAvgAndCount totalAvgAndCount = reviewRepository.getTotalAvgAndCount();

        return ReviewInfoForDashboard.from(totalAvgAndCount, reviewRatingCount);
    }

    @Transactional(readOnly = true)
    public ReviewStatsDto getReviewStats(Long itemId) {
        List<Review> reviews = reviewRepository.findAllByItemId(itemId);

        long totalCount = reviews.size();
        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        double roundedAverage = Math.round(average * 10) / 10.0;

        Map<Integer, Long> ratingCountMap = reviews.stream()
                .collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));

        return new ReviewStatsDto(roundedAverage, totalCount, ratingCountMap);
    }

    @Transactional(readOnly = true)
    public List<ReviewSummaryDto> getLatestReviews(Long itemId) {
        return reviewRepository.findTop3ByOrder_ItemIdOrderByCreatedAtDesc(itemId)
                .stream()
                .map(ReviewSummaryDto::new)
                .toList();
    }
}