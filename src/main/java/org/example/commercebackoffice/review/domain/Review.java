package org.example.commercebackoffice.review.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commercebackoffice.common.entity.BaseEntity;
import org.example.commercebackoffice.order.domain.Order;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "리뷰 엔티티")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "리뷰 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @Schema(description = "리뷰가 작성된 주문", accessMode = Schema.AccessMode.READ_ONLY)
    private Order order;

    @Column(nullable = false)
    @Schema(description = "평점", example = "5", minimum = "1", maximum = "5")
    private Integer rating;

    @Column(nullable = false, length = 255)
    @Schema(description = "리뷰 내용", example = "상품이 매우 만족스러웠습니다.")
    private String content;

    public static Review create(Order order, Integer rating, String content) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1~5 사이여야 합니다.");
        }

        Review review = new Review();
        review.order = order;
        review.rating = rating;
        review.content = content;
        return review;
    }
}