package org.example.commercebackoffice.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {
    //querydsl는 hibernate를 사용하기 때문에 영속성 컨텍스트 필요
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        //JPAQueryFactory를 통해 QuueryDsl 해석
        return new JPAQueryFactory(entityManager);
    }
}
