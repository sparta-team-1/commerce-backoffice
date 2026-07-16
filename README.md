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
| 이재서 | 관리자 관리 기능 및 인증, README 작성                        |

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

| Http Status | ErrorCode           | Description | Message             |
|:-----------:|:--------------------|:------------|:--------------------|
|     400     | PASSWORD_INCORRECT  | 비밀번호 불일치    | 비밀번호가 일치하지 않습니다.    |

작성 요망

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
| Method | URL                 | 설명          |
|:-------|:--------------------|:------------|
| POST   | /reviews            | 리뷰 추가       |
| GET    | /reviews            | 리뷰 리스트 조회   |
| GET    | /reviews/{reviewId} | 리뷰 상세 정보 조회 |
| DELETE | /reviews/{reviewId} | 리뷰 삭제       |

---

## 📊ERD
<img width="1014" height="688" alt="image" src="https://github.com/user-attachments/assets/bbb2b67c-267b-4d2f-a936-d1361fb56e8d" />

## ❗트러블슈팅

