# 🛒e-commerce 백오피스 프로젝트

## 📕목차

1. 프로젝트 소개
2. 프로젝트 기간
3. 팀 구성 및 역할분담 
4. 기술 스택 
5. 프로젝트 구조 
6. 주요 기능 
7. API 명세서 
8. ERD 
9. 트러블슈팅

---

## 📑프로젝트 소개

> Spring Boot와 JPA를 활용한 e-commerce 관리자 시스템

관리자 계정을 통해 상품, 고객, 리뷰를 관리하고, super 관리자 계정으로 다른 관리자 계정을 관리할 수 있는 **관리자 전용 백오피스 시스템**입니다.

---

## 📅프로젝트 기간
2026.07.10 ~ 2026.07.16

---

## 🧑‍💻팀구성 및 역할분담
| 담당자 | 담당 역할                                            |
|:----|:-------------------------------------------------|
| 최용범 | 팀 리더, 전체 일정 관리 및 프로젝트 관리, 주문(Order) 관리 기능        |
| 송나영 | 발표자료 제작 및 발표자, 회원가입 및 전역 예외 처리, 주문 상세 조회 및 취소 기능 |
| 이윤지 | 고객 관리 기능 및 시연 영상 제작                              |
| 강성현 | 상품 관리 기능 및 ppt 제작, 회의 문서 작성                      |
| 이재서 | 관리자 관리 기능 및 인증, 대시보드, README 작성                  |

## 📝주요 기능

### 👤 관리자

- 회원가입 및 로그인
- Http Session 기반 인증
- 관리자 정보 조회
- 관리자 상태 관리
- 관리자 승인 및 역할 변경
- 관리자 정보 변경

### 👥 고객

- 고객 추가
- 고객 정보 조회 (전체 조회, 상세 조회, 키워드 검색, )
- 고객 정보 관리
- 고객 상태 관리
- 고객별 총 구매 금액 조회
- 고객 상세 조회 시 주문 통계 제공
- 고객 삭제

### 📦 상품

- 상품 추가
- 상품 정보 조회
- 상품 정보 관리
- 상품 수량 수정
- 상품 상태 수정
- 상품 삭제

### 🛒 주문

- 주문 추가
- 주문 정보 조회
- 주문 관리
- 주문 상태 수정
- 주문 취소
- 주문 취소 시 재고 처리

### 🧾 리뷰

- 리뷰 추가
- 리뷰 정보 조회
- 리뷰 삭제

### 📖 대시보드

- 대시보드 전체 정보 조회(위젯, 통계 등)

### 🔩 공통 기능

#### Validation

- Bean Validation 적용
- 입력값 검증

#### Exception

- Global Exception Handler를 통해 에러 응답 제공
- 표준화된 에러 응답

---

## 🔧기술 스택

| 항목              | 기술                         |
|:----------------|:---------------------------|
| Language        | Java 21                    |
| Framework       | Spring Boot 4.1.0          |
| Build Tool      | Gradle(groovy)             |
| Version Control | Git / Github               |
| DB              | MySQL 8.x                  |
| ORM             | Spring Data JPA, Hibernate |
| Authentication  | Http Session               |
| API Test Tool   | Postman                    |
| IDE             | Intellij IDEA              |

---

## 📝 API 명세서

### 🔗 기본 정보

- **BASE URL**: `http://localhost:8080/api`
- **데이터 형식**: `Content-Type: application/json`

---

### ⚠ 공통 에러 응답

| Http Status | ErrorCode                       | Description  | Message                   |
|:-----------:|:--------------------------------|:-------------|:--------------------------|
|    400      | PASSWORD_INCORRECT              | 비밀번호 불일치     | 비밀번호가 일치하지 않습니다.          |
|     401     | USER_NOT_ACTIVE                 | 계정 비활성화 상태   | 해당 계정은 활성화 상태가 아닙니다.      |
|     400     | USER_STATUS_NOT_PENDING         | 계정 승인 대기 아님  | 해당 계정은 승인 대기 상태가 아닙니다.    |
|     401     | USER_IS_NOT_SUPER_ADMIN         | 슈퍼 관리자 아님    | 해당 계정은 슈퍼 관리자 계정이 아닙니다.   |
|     400     | UNKNOWN_STATUS                  | 알 수 없는 상태    | 상태 목록에 없는 상태입니다.          |
|     400     | UNKNOWN_ROLE                    | 알 수 없는 역할    | 역할 목록에 없는 역할입니다.          |
|     404     | CUSTOMER_NOT_FOUND              | 고객 조회 실패     | 해당 고객을 찾을 수 없습니다.         |
|     404     | REVIEW_NOT_FOUND                | 리뷰 조회 실패     | 리뷰를 찾을 수 없습니다             |
|     404     | ITEM_NOT_FOUND                  | 상품 조회 실패     | 상품을 찾을 수 없습니다.            |
|     404     | ADMIN_NOT_FOUND                 | 관리자 조회 실패    | 관리자를 찾을 수 없습니다.           |
|     404     | ORDER_NOT_FOUND                 | 주문 조회 실패     | 주문을 찾을 수 없습니다.            |
|     400     | INVALID_ORDER_QUANTITY          | 잘못된 주문 수량    | 주문 수량은 1개 이상이어야 합니다.      |
|     400     | INVALID_ORDER_STATUS_TRANSITION | 잘못된 상태 변경    | 잘못된 상태 변경 순서입니다.          |
|     400     | ORDER_CANCEL_NOT_ALLOWED        | 주문 취소 불가     | 준비중 상태에서만 취소할 수 있습니다.     |
|     400     | ITEM_NOT_ON_SALE                | 상품 판매 중 아님   | 현재 판매 중인 상품이 아닙니다.        |
|     400     | ITEM_STOCK_NOT_ENOUGH           | 상품 재고 부족     | 상품의 재고가 부족합니다.            |
|     400     | INVALID_STOCK_QUANTITY          | 잘못된 복구 재고 수량 | 복구할 재고 수량은 0보다 커야 합니다.    |
|     400     | ITEM_NOT_FOUND_OR_UNMODIFIABLE  | 상품 수정 불가     | 존재하지 않거나 수정할 수 없는 상품입니다.  |
|     400     | CUSTOMER_ALREADY_DELETED        | 이미 탈퇴된 회원    | 이미 탈퇴 처리되었거나 비활성화된 회원입니다. |
|     400     | ALREADY_REVIEWED_ORDER          | 이미 리뷰 작성됨    | 이미 리뷰를 작성한 주문입니다.         |

---

### Auth
| Method | URL          | 설명        |
|:-------|:-------------|:----------|
| POST   | /auth/signup | 관리자 회원 가입 |
| POST   | /auth/login  | 로그인       |
| POST   | /auth/logout | 로그아웃      |

---

### Admin

| Method | URL                       | 설명                |
|:-------|:--------------------------|:------------------|
| GET    | /admins                   | 관리자 계정 리스트 조회     |
| GET    | /admins                   | 관리자 계정 정보 상세 조회   |
| PATCH  | /admins/{adminId}         | 관리자 계정 정보 수정      |
| PATCH  | /admins/{adminId}/role    | 관리자 계정 역할 변경      |
| PATCH  | /admins/{adminId}/status  | 관리자 계정 상태 변경      |
| DELETE | /admins/{adminId}         | 관리자 계정 삭제         |
| PATCH  | /admins/{adminId}/approve | 관리자 계정 승인         |
| PATCH  | /admins/{adminId}/reject  | 관리자 계정 거부         |
| GET    | /admins/me                | 현재 관리자 계정 조회      |
| PATCH  | /admins/me                | 현재 관리자 계정 정보 수정   |
| PATCH  | /admins/me/passowrd       | 현재 관리자 계정 비밀번호 변경 |

---

### Customer

| Method | URL                     | 설명          |
|:-------|:------------------------|:------------|
| GET    | /customers              | 고객 리스트 조회   |
| GET    | /customers/{customerId} | 고객 상제 정보 조회 |
| PUT    | /customers/{customerId} | 고객 정보 수정    |
| PATCH  | /customers/{customerId} | 고객 상태 변경    |
| DELETE | /customers/{customerId} | 고객 삭제       |

---

### Item

| Method | URL                    | 설명          |
|:-------|:-----------------------|:------------|
| POST   | /items                 | 상품 추가       |
| GET    | /items                 | 상품 리스트 조회   |
| GET    | /items/{itemId}        | 상품 상세 정보 조회 |
| PUT    | /items/{itemId}        | 상품 정보 수정    |
| PATCH  | /items/{itemId}/stock  | 상품 수량 수정    |
| PATCH  | /items/{itemId}/status | 상품 상태 수정    |
| DELETE | /items/{itemId}        | 상품 삭제       |

---

### Order
| Method | URL                      | 설명           |
|:-------|:-------------------------|:-------------|
| POST   | /orders                  | 주문 추가        |
| GET    | /orders                  | 주문 리스트 조회    |
| GET    | /orders/{orderId}        | 주문 상세 정보 조회  |
| PATCH  | /orders/{orderId}/status | 주문 상태 수정     |
| PATCH  | /orders/{orderId}/cancel | 주문 취소        |

---

### Review
| Method | URL                 | 설명           |
|:-------|:--------------------|:-------------|
| POST   | /reviews            | 리뷰 추가        |
| GET    | /reviews            | 리뷰 리스트 조회    |
| GET    | /reviews/{reviewId} | 리뷰 상세 정보 조회  |
| DELETE | /reviews/{reviewId} | 리뷰 삭제        |


### Dashboard
| Method | URL        | 설명         |
|:-------|:-----------|:-----------|
| POST   | /dashboard | 대시보드 정보 조회 |

---

## 📊ERD
<img width="1014" height="688" alt="image" src="https://github.com/user-attachments/assets/bbb2b67c-267b-4d2f-a936-d1361fb56e8d" />

---

## ❗트러블슈팅

### QueryParameter 구현

1. **문제**
    - 고객 목록 조회 시 동적 검색 조건을 Query Parameter로 받아야 했지만 구현 경험이 없어 어려움을 겪었습니다.
2. **원인**
   - QueryParameter를 처리하는 방법을 알지 못했습니다.
   - URL의 파라미터를 Controller에서 어떻게 받아오는지도 이해가 부족했습니다.
3. **해결**
    - @RequestParam 어노테이션을 사용하여 클라이언트가 URL(Query String)로 전달한 값을 받을 수 있도록 구현했습니다.
4. **배운 점**
    - @RequestParam을 사용하면 URL의 Query Parameter를 쉽게 받을 수 있습니다.
    - required=false 속성을 사용하면 선택적인 검색 조건도 처리할 수 있다는 것을 알게 되었습니다.

### 동적 조건 조회를 위한 QueryDsl 적용

1. **문제**
    - 동적으로 조회 조건을 변경하기 위해서는 객체 쿼리 언어가 필요하다고 판단했습니다.
2. **원인**
    - JPQL로 구현할 수 있으나, 동적 조건을 처리하기 위해서는 약간의 불편함이 있다고 판단해 다른 대안을 찾아보고자 했습니다.
    - hibernate에서 jpa 표준 criteria를 표준으로 채택하고 있으나 criteria는 가독성이 나쁘다고 판단되어 다른 대안이 필요했습니다.
3. **해결**
    - 많은 프로젝트에서 사용되고 있는 QueryDSL을 채택함으로써 간편함과 가독성을 동시에 챙길 수 있었습니다.
4. **배운 점**
    - 처음 써보는 QueryDSL이라 조금 헤매기도 했지만 BooleanBuilder를 통한 조건값 존재 여부로 간편하게 동적 쿼리 조회를 할 수 있다는 것을 배웠습니다.
