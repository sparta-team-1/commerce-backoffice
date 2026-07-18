package org.example.commercebackoffice.review.repository;

import org.example.commercebackoffice.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//리뷰 검색 메서드
    @Query("SELECT r FROM Review r " +
            "WHERE (:rating IS NULL OR r.rating = :rating) " +
            "AND (:keyword IS NULL " +
            "     OR r.order.customer.name LIKE %:keyword% " +
            "     OR r.order.item.name LIKE %:keyword%)")
    Page<Review> search(@Param("rating") Integer rating,
                        @Param("keyword") String keyword,
                        Pageable pageable);

    //리뷰 상세 조희 메서드
    //상세 조회 시 필요한 연관 객체를 한 번에 가져오도록 ,JOIN FETCH를 사용
    @Query("""
    SELECT r 
    FROM Review r
    JOIN FETCH r.order o
    JOIN FETCH o.customer c
    JOIN FETCH o.item i
    WHERE r.id = :reviewId
""")
    Optional<Review> findDetailById(
            @Param("reviewId") Long reviewId
    );

    boolean existsByOrderId(Long orderId);

    List<Review> findTop3ByOrder_ItemIdOrderByCreatedAtDesc(Long itemId);

    @Query("SELECT r FROM Review r WHERE r.order.item.id = :itemId")
    List<Review> findAllByItemId(@Param("itemId") Long itemId);
}