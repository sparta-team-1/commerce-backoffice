package org.example.commercebackoffice.review.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.order.domain.Order;
import org.example.commercebackoffice.order.domain.dto.PageResponseDto;
import org.example.commercebackoffice.order.repository.OrderRepository;
import org.example.commercebackoffice.review.domain.Review;
import org.example.commercebackoffice.review.domain.dto.ReviewCreateRequestDto;
import org.example.commercebackoffice.review.domain.dto.ReviewListResponseDto;
import org.example.commercebackoffice.review.domain.dto.ReviewResponseDto;
import org.example.commercebackoffice.review.domain.dto.ReviewSearchCondition;
import org.example.commercebackoffice.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewCreateRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

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
}