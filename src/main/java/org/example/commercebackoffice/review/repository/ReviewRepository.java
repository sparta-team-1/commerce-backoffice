package org.example.commercebackoffice.review.repository;

import org.example.commercebackoffice.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE (:rating IS NULL OR r.rating = :rating) " +
            "AND (:keyword IS NULL " +
            "     OR r.order.customer.name LIKE %:keyword% " +
            "     OR r.order.item.name LIKE %:keyword%)")
    Page<Review> search(@Param("rating") Integer rating,
                        @Param("keyword") String keyword,
                        Pageable pageable);
}