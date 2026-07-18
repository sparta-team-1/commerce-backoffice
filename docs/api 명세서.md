# Commerce Back-Office API
Commerce Back-Office API

## Version: 1.0

### /api/items/{itemId}

#### GET
##### Summary:

상품 상세 조회

##### Description:

상품 ID를 이용하여 상품 정보를 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| itemId | path | 상품 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 상품 조회 성공 |
| 404 | 상품을 찾을 수 없음 |

#### PUT
##### Summary:

상품 정보 수정

##### Description:

상품의 기본 정보를 수정합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| itemId | path | 상품 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 상품 수정 성공 |
| 404 | 상품을 찾을 수 없음 |

#### DELETE
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| itemId | path | 상품 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 204 | 상품 삭제 성공 |
| 404 | 상품을 찾을 수 없음 |

### /api/customers/{customerId}

#### GET
##### Summary:

고객 상세 조회

##### Description:

고객 ID를 이용하여 고객 상세 정보를 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| customerId | path | 고객 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 고객 조회 성공 |
| 404 | 고객을 찾을 수 없음 |

#### PUT
##### Summary:

고객 정보 수정

##### Description:

고객의 기본 정보를 수정합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| customerId | path | 고객 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 고객 정보 수정 성공 |
| 404 | 고객을 찾을 수 없음 |

#### DELETE
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| customerId | path | 고객 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 204 | 고객 삭제 성공 |
| 404 | 고객을 찾을 수 없음 |

#### PATCH
##### Summary:

고객 상태 변경

##### Description:

고객의 상태를 변경합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| customerId | path | 고객 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 고객 상태 변경 성공 |
| 404 | 고객을 찾을 수 없음 |

### /api/reviews

#### GET
##### Summary:

리뷰 목록 조회

##### Description:

검색 조건과 페이징 정보를 이용하여 리뷰 목록을 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| condition | query | 리뷰 검색 조건 | Yes | [ReviewSearchCondition](#ReviewSearchCondition) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 리뷰 목록 조회 성공 |

#### POST
##### Summary:

리뷰 등록

##### Description:

주문에 대한 리뷰를 등록합니다.

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | 리뷰 등록 성공 |
| 400 | 잘못된 요청 |

### /api/orders

#### GET
##### Summary:

주문 목록 조회

##### Description:

검색 조건과 페이징 정보를 이용하여 주문 목록을 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| condition | query |  | Yes | [OrderSearchCondition](#OrderSearchCondition) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 주문 목록 조회 성공 |

#### POST
##### Summary:

주문 생성

##### Description:

새로운 주문을 생성합니다.

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | 주문 생성 성공 |
| 400 | 잘못된 요청 |

### /api/items

#### GET
##### Summary:

상품 목록 조회

##### Description:

검색 조건과 페이징 정보를 이용하여 상품 목록을 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| keyword | query | 검색 키워드 | No | string |
| category | query | 카테고리 | No | string |
| status | query | 상품 상태 | No | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 상품 목록 조회 성공 |

#### POST
##### Summary:

상품 등록

##### Description:

새로운 상품을 등록합니다.

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | 상품 등록 성공 |
| 400 | 잘못된 요청 |

### /api/auth/register

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 회원가입 신청 성공 |
| 400 | 잘못된 요청 |

### /api/auth/register/super

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /api/auth/logout

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 로그아웃 성공 |

### /api/auth/login

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 로그인 성공 |
| 401 | 이메일 또는 비밀번호 불일치 |
| 403 | 승인되지 않은 계정 |

### /api/orders/{orderId}/status

#### PATCH
##### Summary:

주문 상태 변경

##### Description:

주문의 상태를 변경합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| orderId | path | 주문 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 주문 상태 변경 성공 |
| 404 | 주문을 찾을 수 없음 |

### /api/orders/{orderId}/cancel

#### PATCH
##### Summary:

주문 취소

##### Description:

주문을 취소하고 취소 사유를 저장합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| orderId | path | 주문 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 주문 취소 성공 |
| 404 | 주문을 찾을 수 없음 |

### /api/items/{itemId}/stock

#### PATCH
##### Summary:

상품 재고 변경

##### Description:

상품의 재고 수량을 변경합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| itemId | path | 상품 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 상품 재고 변경 성공 |
| 404 | 상품을 찾을 수 없음 |

### /api/items/{itemId}/status

#### PATCH
##### Summary:

상품 상태 변경

##### Description:

상품의 판매 상태를 변경합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| itemId | path | 상품 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 상품 상태 변경 성공 |
| 404 | 상품을 찾을 수 없음 |

### /api/admins/{id}

#### GET
##### Summary:

관리자 상세 조회

##### Description:

관리자 ID를 이용하여 관리자 상세 정보를 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | 관리자 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 조회 성공 |
| 404 | 관리자를 찾을 수 없음 |

#### DELETE
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | 관리자 ID | Yes | long |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 204 | 관리자 삭제 성공 |
| 403 | 권한 없음 |
| 404 | 관리자를 찾을 수 없음 |

#### PATCH
##### Summary:

관리자 계정 수정

##### Description:

관리자 계정 ID를 이용하여 관리자 계정을 수정합니다. SUPER 관리자만 가능합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | 관리자 ID | Yes | long |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 관리자 정보 수정 성공 |
| 403 | 권한 없음 |
| 404 | 관리자를 찾을 수 없음 |

### /api/admins/{id}/status

#### PATCH
##### Summary:

관리자 상태 변경

##### Description:

관리자의 상태를 변경합니다. SUPER 관리자만 가능합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | 관리자 ID | Yes | long |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 관리자 상태 변경 성공 |
| 403 | 권한 없음 |
| 404 | 관리자를 찾을 수 없음 |

### /api/admins/{id}/role

#### PATCH
##### Summary:

관리자 역할 변경

##### Description:

관리자의 역할을 변경합니다. SUPER 관리자만 가능합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | 관리자 ID | Yes | long |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 관리자 역할 변경 성공 |
| 403 | 권한 없음 |
| 404 | 관리자를 찾을 수 없음 |

### /api/admins/{id}/reject

#### PATCH
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | 관리자 ID | Yes | long |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 관리자 거부 성공 |
| 403 | 권한 없음 |
| 404 | 관리자를 찾을 수 없음 |

### /api/admins/{id}/approve

#### PATCH
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | 관리자 ID | Yes | long |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 관리자 승인 성공 |
| 403 | 권한 없음 |
| 404 | 관리자를 찾을 수 없음 |

### /api/admins/me

#### GET
##### Summary:

내 관리자 정보 조회

##### Description:

현재 로그인한 관리자의 정보를 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 조회 성공 |

#### PATCH
##### Summary:

내 관리자 정보 수정

##### Description:

현재 로그인한 관리자의 정보를 수정합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 수정 성공 |

### /api/admins/me/password

#### PATCH
##### Summary:

내 비밀번호 변경

##### Description:

현재 로그인한 관리자의 비밀번호를 변경합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userInfo | query |  | Yes | [SessionUser](#SessionUser) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 비밀번호 변경 성공 |

### /api/reviews/{reviewId}

#### GET
##### Summary:

리뷰 상세 조회

##### Description:

리뷰 ID를 이용하여 리뷰 상세 정보를 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| reviewId | path | 리뷰 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 리뷰 조회 성공 |
| 404 | 리뷰를 찾을 수 없음 |

#### DELETE
##### Summary:

리뷰 삭제

##### Description:

리뷰를 삭제합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| reviewId | path | 리뷰 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 204 | 리뷰 삭제 성공 |
| 404 | 리뷰를 찾을 수 없음 |

### /api/orders/{orderId}

#### GET
##### Summary:

주문 상세 조회

##### Description:

주문 ID를 이용하여 주문 상세 정보를 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| orderId | path | 주문 ID | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 주문 조회 성공 |
| 404 | 주문을 찾을 수 없음 |

### /api/dashboard

#### GET
##### Summary:

대시보드 정보 조회

##### Description:

대시보드에 필요한 통계 정보를 조회합니다.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 대시보드 정보 조회 성공 |
| 500 | 서버 오류 |

### /api/customers

#### GET
##### Summary:

고객 목록 조회

##### Description:

검색 조건과 페이징 정보를 이용하여 고객 목록을 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| keyword | query | 검색 키워드(이름, 이메일, 전화번호) | No | string |
| page | query | 페이지 번호 | No | integer |
| size | query | 페이지 크기 | No | integer |
| sortBy | query | 정렬 기준 | No | string |
| sortOrder | query | 정렬 방향 | No | string |
| status | query | 고객 상태 | No | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 고객 목록 조회 성공 |

### /api/admins

#### GET
##### Summary:

관리자 목록 조회

##### Description:

조건에 따라 관리자 목록을 조회합니다.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| role | query | 관리자 역할 | No | string,null |
| status | query | 관리자 상태 | No | string,null |
| search | query | 검색어(이름, 이메일) | No | string,null |
| page | query | 페이지 번호 | Yes | integer |
| limit | query | 페이지 크기 | Yes | integer |
| sortBy | query | 정렬 기준 | Yes | string |
| sortOrder | query | 정렬 방향 | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | 조회 성공 |
