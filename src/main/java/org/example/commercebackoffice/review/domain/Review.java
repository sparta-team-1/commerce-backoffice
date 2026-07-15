package org.example.commercebackoffice.review.domain;


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
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 255)
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