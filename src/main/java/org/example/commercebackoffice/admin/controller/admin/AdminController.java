package org.example.commercebackoffice.admin.controller.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.admin.dto.request.*;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminDetailResponse;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminPageResponse;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminResponse;
import org.example.commercebackoffice.admin.controller.auth.SessionUser;
import org.example.commercebackoffice.admin.service.AdminSearchCondition;
import org.example.commercebackoffice.admin.service.AdminService;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    //관리자 계정 수정(super 관리자) 엔드포인트
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponse>> editAdmin(@PathVariable Long id, @Valid @RequestBody AdminEditRequest editRequest,
                                                                @SessionAttribute SessionUser userInfo) {
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.editAdminInfo(curAdminId, id, editRequest);

        return ResponseEntity
                .status(SuccessCode.ADMIN_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_INFO_UPDATE_SUCCESS, resBody));
    }

    //관리자 역할 수정 엔드포인트
    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<AdminResponse>> changeAdminRole(@PathVariable Long id,
                                                                      @RequestBody AdminChangeRoleRequest changeRequest,
                                                                      @SessionAttribute SessionUser userInfo) {
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeAdminRole(curAdminId, id, changeRequest.role());

        return ResponseEntity
                .status(SuccessCode.ADMIN_ROLE_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_ROLE_UPDATE_SUCCESS, resBody));
    }

    //관리자 상태 수정 엔드포인트
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AdminResponse>> changeAdminStatus(@PathVariable Long id,
                                                                        @RequestBody AdminChangeStatusRequest changeStatusRequest,
                                                                        @SessionAttribute SessionUser userInfo) {
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeAdminStatus(curAdminId, id, changeStatusRequest.status());

        return ResponseEntity
                .status(SuccessCode.ADMIN_STATUS_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_STATUS_UPDATE_SUCCESS, resBody));
    }

    //관리자 계정 삭제 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Long id,
                                                         @SessionAttribute SessionUser userInfo) {
        Long curAdminId = userInfo.id();
        adminService.deleteAdmin(curAdminId, id);

        // 기존 noContent() 대신 공통 규격 바디 반환을 위해 200 OK와 null 객체 포장
        return ResponseEntity
                .status(SuccessCode.ADMIN_DELETE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_DELETE_SUCCESS, null));
    }

    //관리자 계정 승인 엔드포인트
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<Void>> approveAdmin(@PathVariable Long id,
                                                          @SessionAttribute SessionUser userInfo) {
        Long curAdminId = userInfo.id();
        adminService.approveAdmin(curAdminId, id);

        return ResponseEntity
                .status(SuccessCode.ADMIN_APPROVE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_APPROVE_SUCCESS, null));
    }

    //관리자 계정 거부 엔드포인트
    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectAdmin(@PathVariable Long id,
                                                         @SessionAttribute SessionUser userInfo,
                                                         @Valid @RequestBody AdminRejectRequest rejectRequest) {
        Long curAdminId = userInfo.id();
        adminService.rejectAdmin(curAdminId, id, rejectRequest);

        return ResponseEntity
                .status(SuccessCode.ADMIN_REJECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_REJECT_SUCCESS, null));
    }

    //현재 사용자 계정 정보 조회 엔드포인트
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdminResponse>> getCurrentUser(@SessionAttribute SessionUser userInfo) {
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.me(curAdminId);

        return ResponseEntity
                .status(SuccessCode.MY_PROFILE_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.MY_PROFILE_SELECT_SUCCESS, resBody));
    }
    //현재 사용자 계정 정보 수정 엔드포인트
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<AdminResponse>> editCurrentUser(@SessionAttribute SessionUser userInfo,
                                                                      @Valid @RequestBody AdminEditRequest editRequest) {
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.editCurrentAdminInfo(curAdminId, editRequest);

        return ResponseEntity
                .status(SuccessCode.MY_PROFILE_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.MY_PROFILE_UPDATE_SUCCESS, resBody));
    }

    //현재 사용자 계정 비밀번호 수정 엔드포인트
    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<AdminResponse>> changeCurrentUserPassword(@SessionAttribute SessionUser userInfo,
                                                                                @Valid @RequestBody AdminChangePasswordRequest changeRequest) {
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeCurrentAdminPassword(curAdminId, changeRequest);

        return ResponseEntity
                .status(SuccessCode.PASSWORD_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.PASSWORD_UPDATE_SUCCESS, resBody));
    }

    //동적 쿼리 조회 엔드포인트
    @GetMapping
    public ResponseEntity<ApiResponse<AdminPageResponse>> getAdminsWithCondition(@Nullable @RequestParam String role,
                                                                                 @Nullable @RequestParam String status,
                                                                                 @Nullable @RequestParam String search,
                                                                                 @RequestParam Integer page,
                                                                                 @RequestParam Integer limit,
                                                                                 @RequestParam String sortBy,
                                                                                 @RequestParam String sortOrder) {
        AdminSearchCondition searchCondition = new AdminSearchCondition(page, limit, sortBy, sortOrder, status, role, search);
        AdminPageResponse resBody = adminService.getAdminsWithCondition(searchCondition);

        return ResponseEntity
                .status(SuccessCode.ADMIN_LIST_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_LIST_SELECT_SUCCESS, resBody));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminDetailResponse>> getAdminById(@PathVariable Long id) {
        AdminDetailResponse resBody = adminService.getAdminDetail(id);

        return ResponseEntity
                .status(SuccessCode.ADMIN_DETAIL_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_DETAIL_SELECT_SUCCESS, resBody));
    }
}
