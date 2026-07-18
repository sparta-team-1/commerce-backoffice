package org.example.commercebackoffice.common.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

        // 1. 주문 API (Order)
        ORDER_CREATE_SUCCESS(HttpStatus.CREATED, "주문이 완료되었습니다."),
        ORDER_LIST_SELECT_SUCCESS(HttpStatus.OK, "주문 목록 조회에 성공하였습니다."),
        ORDER_DETAIL_SELECT_SUCCESS(HttpStatus.OK, "주문 상세 조회에 성공하였습니다."),
        ORDER_STATUS_UPDATE_SUCCESS(HttpStatus.OK, "주문 상태가 성공적으로 수정되었습니다."),
        ORDER_CANCEL_SUCCESS(HttpStatus.OK, "주문이 취소되었습니다."),

        // 2. 리뷰 API (Review)
        REVIEW_CREATE_SUCCESS(HttpStatus.CREATED, "리뷰가 성공적으로 등록되었습니다."),
        REVIEW_LIST_SELECT_SUCCESS(HttpStatus.OK, "리뷰 목록 조회에 성공하였습니다."),
        REVIEW_DETAIL_SELECT_SUCCESS(HttpStatus.OK, "리뷰 상세 조회에 성공하였습니다."),
        REVIEW_DELETE_SUCCESS(HttpStatus.OK, "리뷰가 삭제되었습니다."),

        // 3. 관리자 API & 인증 (Admin & Auth)
        ADMIN_INFO_UPDATE_SUCCESS(HttpStatus.OK, "관리자 정보가 수정되었습니다."),
        ADMIN_ROLE_UPDATE_SUCCESS(HttpStatus.OK, "관리자 권한이 변경되었습니다."),
        ADMIN_STATUS_UPDATE_SUCCESS(HttpStatus.OK, "관리자 상태가 변경되었습니다."),
        ADMIN_DELETE_SUCCESS(HttpStatus.OK, "관리자 계정이 삭제되었습니다."),
        ADMIN_APPROVE_SUCCESS(HttpStatus.OK, "관리자 가입 승인이 완료되었습니다."),
        ADMIN_REJECT_SUCCESS(HttpStatus.OK, "관리자 가입 거절 처리가 완료되었습니다."),
        ADMIN_DETAIL_SELECT_SUCCESS(HttpStatus.OK, "관리자 상세 정보 조회에 성공하였습니다."),
        ADMIN_LIST_SELECT_SUCCESS(HttpStatus.OK, "관리자 목록 조회에 성공하였습니다."),

        MY_PROFILE_SELECT_SUCCESS(HttpStatus.OK, "내 프로필 조회에 성공하였습니다."),
        MY_PROFILE_UPDATE_SUCCESS(HttpStatus.OK, "내 프로필 정보가 수정되었습니다."),
        PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호가 성공적으로 변경되었습니다."),

        LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
        LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃되었습니다."),
        ADMIN_SIGNUP_SUCCESS(HttpStatus.CREATED, "관리자 회원가입 신청이 완료되었습니다."),
        TEST_SUPER_ADMIN_CREATE_SUCCESS(HttpStatus.CREATED, "테스트용 슈퍼 관리자 계정이 추가되었습니다."),

        // 4. 고객 API (Customer)
        CUSTOMER_LIST_SELECT_SUCCESS(HttpStatus.OK, "고객 목록 조회에 성공하였습니다."),
        CUSTOMER_DETAIL_SELECT_SUCCESS(HttpStatus.OK, "고객 상세 조회에 성공하였습니다."),
        CUSTOMER_INFO_UPDATE_SUCCESS(HttpStatus.OK, "고객 정보가 수정되었습니다."),
        CUSTOMER_STATUS_UPDATE_SUCCESS(HttpStatus.OK, "고객 상태가 성공적으로 변경되었습니다."),
        CUSTOMER_DELETE_SUCCESS(HttpStatus.OK, "고객 정보가 삭제(탈퇴)되었습니다."),

        // 5. 상품 API (Item / Product)
        ITEM_CREATE_SUCCESS(HttpStatus.CREATED, "상품 등록이 완료되었습니다."),
        ITEM_KEYWORD_SELECT_SUCCESS(HttpStatus.OK, "키워드 기반 상품 검색에 성공하였습니다."),
        ITEM_CATEGORY_SELECT_SUCCESS(HttpStatus.OK, "카테고리별 상품 조회에 성공하였습니다."),
        ITEM_ON_SALE_SELECT_SUCCESS(HttpStatus.OK, "판매 중인 상품 목록 조회에 성공하였습니다."),
        ITEM_PAGING_SELECT_SUCCESS(HttpStatus.OK, "가격 내림차순 상품 페이징 조회에 성공하였습니다."),
        ITEM_DETAIL_SELECT_SUCCESS(HttpStatus.OK, "상품 단건(상세) 조회에 성공하였습니다."),
        ITEM_INFO_UPDATE_SUCCESS(HttpStatus.OK, "상품 정보가 성공적으로 수정되었습니다."),
        ITEM_STOCK_UPDATE_SUCCESS(HttpStatus.OK, "상품 재고 변경 및 상태 전환이 완료되었습니다."),
        ITEM_DELETE_SUCCESS(HttpStatus.OK, "상품이 삭제되었습니다."),

        //6. 대시보 API
        DASHBOARD_SELECT_SUCCESS(HttpStatus.OK, "대시보드 정보 조회에 성공했습니다.");

        private final HttpStatus httpStatus;
        private final String message;
        
        SuccessCode(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }
    }