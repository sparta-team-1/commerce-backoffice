package org.example.commercebackoffice.item.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.common.entity.BaseEntity;
import org.example.commercebackoffice.item.domain.enums.ItemStatus;

@Getter
@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String name;
    private String category;
    private Long price;
    private Integer stock;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;
}