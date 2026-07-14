package org.example.commercebackoffice.admin.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminResponse;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.commercebackoffice.admin.domain.QAdmin.admin;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    //쿼리 파라미터를 통해 조건 조회
    public Page<AdminResponse> searchWithConditions(AdminSearchCondition searchCondition,
                                                    Pageable pageable) {
        BooleanBuilder condition = allCondition(searchCondition);

        //querydsl 검색 조건 추가(null은 무시됨)
        List<AdminResponse> content = queryFactory
                //projection을 통해 AdminResponse로 매핑
                .select(Projections.constructor(AdminResponse.class,
                        admin.id.as("id"),
                        admin.email.as("email"),
                        admin.name.as("name"),
                        admin.phoneNumber.as("phoneNumber"),
                        admin.role.as("role"),
                        admin.status.as("status"),
                        admin.createdAt.as("createdAt"),
                        admin.approvedAt.as("approvedAt")
                        ))
                .from(admin)
                .where(condition)
                .orderBy(orderBy(searchCondition.sortBy(), searchCondition.sortOrder()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .toList();

        Long total = queryFactory.select(admin.count())
                .from(admin)
                .where(condition)
                .fetchOne();

        //pagination 정보까지 반환하기 위해 PageImpl로 래핑
        return new PageImpl<>(
                content,
                pageable,
                total != null ? total : 0
        );
    }

    //검색 조건 취합
    private BooleanBuilder allCondition(AdminSearchCondition searchCondition) {
        BooleanBuilder builder = new BooleanBuilder();

        return builder
                .and(hasRole(searchCondition.role()))
                .and(hasStatus(searchCondition.status()))
                .and(searchWith(searchCondition.search()));
    }

    //role 있으면 조건 추가 없으면 null 반환
    private BooleanExpression hasRole(String role) {
        return hasText(role) ? admin.role.eq(AdminRole.from(role)) : null;
    }

    //status 있으면 조건 추가 없으면 null 반환
    private BooleanExpression hasStatus(String status) {
        return hasText(status) ? admin.status.eq(AdminStatus.from(status)) : null;
    }

    //search 있으면 조건 추가 없으면 null 반환
    private BooleanExpression searchWith(String search) {
        if (!hasText(search)) {
            return null;
        } else if (search.contains("@")) {      //email 이면, 이름에 @이 없다고 가정
            return admin.email.eq(search);
        }  else {
            return admin.name.eq(search);
        }
    }

    //정렬 조건 간 분기
    private OrderSpecifier<?> orderBy(String sortBy, String sortOrder) {
        if(!hasText(sortBy)) {
            return null;
        }

        ComparableExpressionBase<?> targetPath = switch (sortBy) {
            case "name" -> admin.name;
            case "email" -> admin.email;
            case "role" -> admin.role;
            case "status" -> admin.status;
            case "createdAt" -> admin.createdAt;
            case "approvedAt" -> admin.approvedAt;
            default -> null;
        };

        if (targetPath == null) {
            return null;
        }

        return "asc".equals(sortOrder) ? targetPath.asc() : targetPath.desc();
    }
}
