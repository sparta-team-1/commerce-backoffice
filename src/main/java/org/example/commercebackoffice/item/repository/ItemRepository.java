package org.example.commercebackoffice.item.repository;

import org.example.commercebackoffice.item.domain.Item;
import org.example.commercebackoffice.item.domain.enums.ItemStatus;
import org.example.commercebackoffice.item.dto.response.CategoryMapping;
import org.example.commercebackoffice.item.dto.response.ItemCountInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByIdAndStatusNot(Long id, ItemStatus status);

    // 조건이 들어올 때만 필터링이 걸리는 동적 쿼리
    @Query("SELECT i FROM Item i WHERE i.status != 'DISCONTINUED' " +
            "AND (:keyword IS NULL OR i.name LIKE %:keyword%) " +
            "AND (:category IS NULL OR i.category = :category) " +
            "AND (:status IS NULL OR i.status = :status)")
    Page<Item> searchItems(
            @Param("keyword") String keyword,
            @Param("category") String category,
            @Param("status") ItemStatus status,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT new org.example.commercebackoffice.item.dto.response.ItemCountInfo(
            COUNT(i) OVER(),
            COUNT(CASE WHEN  0 < i.stock AND i.stock <= 5 THEN 1 END) OVER(),
            COUNT(CASE WHEN i.status = 'SOLD_OUT' THEN 1 END) OVER()
        )
        FROM Item i
    """)
    ItemCountInfo getItemInfoCount();

    @Query("""
        SELECT DISTINCT i.category AS category, COUNT(i) AS categoryCount
        FROM Item i
        GROUP BY i.category
    """)
    List<CategoryMapping> getCategoryCounts();
}