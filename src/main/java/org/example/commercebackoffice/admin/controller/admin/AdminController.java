package org.example.commercebackoffice.admin.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminChangeRoleRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminChangeStatusRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminEditRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminRejectRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminResponse;
import org.example.commercebackoffice.admin.controller.auth.SessionUser;
import org.example.commercebackoffice.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    //관리자 계정 수정(super 관리자) 엔드포인트
    @PatchMapping("/{id}")
    public ResponseEntity<?> editAdmin(@PathVariable Long id, @Valid @RequestBody AdminEditRequest editRequest,
                                       @SessionAttribute SessionUser userInfo) {
        //세션에서 가져온 정보에서 id 추출
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.editAdminInfo(curAdminId, id, editRequest);

        return ResponseEntity.ok(resBody);
    }

    //관리자 역할 수정 엔드포인트
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> changeAdminRole(@PathVariable Long id,
                                             @RequestBody AdminChangeRoleRequest changeRequest,
                                             @SessionAttribute SessionUser userInfo) {
        //세션에서 가져온 정보에서 id 추출
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeAdminRole(curAdminId, id, changeRequest.role());

        return ResponseEntity.ok(resBody);
    }

    //관리자 상태 수정 엔드포인트
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeAdminStatus(@PathVariable Long id,
                                               @RequestBody AdminChangeStatusRequest changeStatusRequest,
                                               @SessionAttribute SessionUser userInfo) {
        //세션에서 가져온 정보에서 id 추출
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeAdminStatus(curAdminId, id, changeStatusRequest.status());

        return ResponseEntity.ok(resBody);
    }

    //관리자 계정 삭제 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id,
                                         @SessionAttribute SessionUser userInfo) {
        //세션에서 가져온 정보에서 id 추출
        Long curAdminId = userInfo.id();

        adminService.deleteAdmin(curAdminId, id);

        return ResponseEntity.noContent().build();
    }

    //관리자 계정 승인 엔드포인트
    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approveAdmin(@PathVariable Long id,
                                          @SessionAttribute SessionUser userInfo) {
        //세션에서 가져온 정보에서 id 추출
        Long curAdminId = userInfo.id();
        adminService.approveAdmin(curAdminId, id);

        return ResponseEntity.ok().build();
    }

    //관리자 계정 거부 엔드포인트
    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> rejectAdmin(@PathVariable Long id,
                                         @SessionAttribute SessionUser userInfo,
                                         @Valid @RequestBody AdminRejectRequest rejectRequest) {
        //세션에서 가져온 정보에서 id 추출
        Long curAdminId = userInfo.id();
        adminService.rejectAdmin(curAdminId, id, rejectRequest);

        return ResponseEntity.ok().build();
    }
}
