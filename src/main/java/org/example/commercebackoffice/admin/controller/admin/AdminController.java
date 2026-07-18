package org.example.commercebackoffice.admin.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 관리", description = "관리자 계정 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Operation(
            summary = "관리자 계정 수정",
            description = "관리자 계정 ID를 이용하여 관리자 계정을 수정합니다. SUPER 관리자만 가능합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "관리자 정보 수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_IS_NOT_SUPER_ADMIN",
                                    value = """
                                            {
                                              "status": 401,
                                              "message": "USER IS NOT SUPER ADMIN",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "관리자를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ADMIN_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "ADMIN NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminResponse>> editAdmin(
            @Parameter(description = "관리자 ID", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "관리자 계정 정보 수정 요청",
                    required = true
            )
            @Valid @RequestBody AdminEditRequest editRequest,
            @SessionAttribute SessionUser userInfo) {

        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.editAdminInfo(curAdminId, id, editRequest);

        return ResponseEntity
                .status(SuccessCode.ADMIN_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_INFO_UPDATE_SUCCESS, resBody));
    }

    @Operation(
            summary = "관리자 역할 변경",
            description = "관리자의 역할을 변경합니다. SUPER 관리자만 가능합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "관리자 역할 변경 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_IS_NOT_SUPER_ADMIN",
                                    value = """
                                            {
                                              "status": 401,
                                              "message": "USER IS NOT SUPER ADMIN",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "관리자를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ADMIN_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "ADMIN NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("/{id}/role")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminResponse>> changeAdminRole(
            @Parameter(description = "관리자 ID", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "관리자 계정 역할 변경 요청",
                    required = true
            )
            @RequestBody AdminChangeRoleRequest changeRequest,
            @SessionAttribute SessionUser userInfo) {

        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeAdminRole(curAdminId, id, changeRequest.role());

        return ResponseEntity
                .status(SuccessCode.ADMIN_ROLE_UPDATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_ROLE_UPDATE_SUCCESS, resBody));
    }

    @Operation(
            summary = "관리자 상태 변경",
            description = "관리자의 상태를 변경합니다. SUPER 관리자만 가능합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "관리자 상태 변경 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_IS_NOT_SUPER_ADMIN",
                                    value = """
                                            {
                                              "status": 401,
                                              "message": "USER IS NOT SUPER ADMIN",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "관리자를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ADMIN_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "ADMIN NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminResponse>> changeAdminStatus(
            @Parameter(description = "관리자 ID", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "관리자 계정 상태 변경 요청",
                    required = true
            )
            @RequestBody AdminChangeStatusRequest changeStatusRequest,
            @SessionAttribute SessionUser userInfo) {

        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeAdminStatus(curAdminId, id, changeStatusRequest.status());

        return ResponseEntity
                .status(SuccessCode.ADMIN_STATUS_UPDATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_STATUS_UPDATE_SUCCESS, resBody));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "관리자 삭제 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "success": true,
                                  "message": "관리자 계정이 삭제되었습니다.",
                                  "data": null
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_IS_NOT_SUPER_ADMIN",
                                    value = """
                                            {
                                              "status": 401,
                                              "message": "USER IS NOT SUPER ADMIN",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "관리자를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ADMIN_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "ADMIN NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> deleteAdmin(
            @Parameter(description = "관리자 ID", example = "1", required = true)
            @PathVariable Long id,
            @SessionAttribute SessionUser userInfo) {

        Long curAdminId = userInfo.id();
        adminService.deleteAdmin(curAdminId, id);

        // 기존 noContent() 대신 공통 규격 바디 반환을 위해 200 OK와 null 객체 포장
        return ResponseEntity
                .status(SuccessCode.ADMIN_DELETE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_DELETE_SUCCESS, null));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "관리자 승인 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "success": true,
                                  "message": "관리자 계정이 승인되었습니다.",
                                  "data": null
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_IS_NOT_SUPER_ADMIN",
                                    value = """
                                            {
                                              "status": 401,
                                              "message": "USER IS NOT SUPER ADMIN",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "관리자를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ADMIN_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "ADMIN NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("/{id}/approve")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> approveAdmin(
            @Parameter(description = "관리자 ID", example = "1", required = true)
            @PathVariable Long id,
            @SessionAttribute SessionUser userInfo) {

        Long curAdminId = userInfo.id();
        adminService.approveAdmin(curAdminId, id);

        return ResponseEntity
                .status(SuccessCode.ADMIN_APPROVE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_APPROVE_SUCCESS, null));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "관리자 거부 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "success": true,
                                  "message": "관리자 계정이 거부되었습니다.",
                                  "data": null
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_IS_NOT_SUPER_ADMIN",
                                    value = """
                                            {
                                              "status": 401,
                                              "message": "USER IS NOT SUPER ADMIN",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "관리자를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ADMIN_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "ADMIN NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("/{id}/reject")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> rejectAdmin(
            @Parameter(description = "관리자 ID", example = "1", required = true)
            @PathVariable Long id,
            @SessionAttribute SessionUser userInfo,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "관리자 계정 삭제 요청",
                    required = true
            )
            @Valid @RequestBody AdminRejectRequest rejectRequest) {

        Long curAdminId = userInfo.id();
        adminService.rejectAdmin(curAdminId, id, rejectRequest);

        return ResponseEntity
                .status(SuccessCode.ADMIN_REJECT_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_REJECT_SUCCESS, null));
    }

    @Operation(
            summary = "내 관리자 정보 조회",
            description = "현재 로그인한 관리자의 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/me")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminResponse>>
    getCurrentUser(@SessionAttribute SessionUser userInfo) {

        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.me(curAdminId);

        return ResponseEntity
                .status(SuccessCode.MY_PROFILE_SELECT_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.MY_PROFILE_SELECT_SUCCESS, resBody));
    }

    @Operation(
            summary = "내 관리자 정보 수정",
            description = "현재 로그인한 관리자의 정보를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    //현재 사용자 계정 정보 수정 엔드포인트
    @PatchMapping("/me")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminResponse>> editCurrentUser(
            @SessionAttribute SessionUser userInfo,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "내 관리자 계정 정보 수정 요청",
                    required = true
            )
            @Valid @RequestBody AdminEditRequest editRequest) {
        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.editCurrentAdminInfo(curAdminId, editRequest);

        return ResponseEntity
                .status(SuccessCode.MY_PROFILE_UPDATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.MY_PROFILE_UPDATE_SUCCESS, resBody));
    }

    @Operation(
            summary = "내 비밀번호 변경",
            description = "현재 로그인한 관리자의 비밀번호를 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    })
    @PatchMapping("/me/password")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminResponse>> changeCurrentUserPassword(
            @SessionAttribute SessionUser userInfo,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "내 관리자 계정 비밀번호 수정 요청",
                    required = true
            )
            @Valid @RequestBody AdminChangePasswordRequest changeRequest) {

        Long curAdminId = userInfo.id();
        AdminResponse resBody = adminService.changeCurrentAdminPassword(curAdminId, changeRequest);

        return ResponseEntity
                .status(SuccessCode.PASSWORD_UPDATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.PASSWORD_UPDATE_SUCCESS, resBody));
    }

    @Operation(
            summary = "관리자 목록 조회",
            description = "조건에 따라 관리자 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminPageResponse>> getAdminsWithCondition(

            @Parameter(description = "관리자 역할", example = "SUPER")
            @Nullable @RequestParam String role,

            @Parameter(description = "관리자 상태", example = "ACTIVE")
            @Nullable @RequestParam String status,

            @Parameter(description = "검색어(이름, 이메일)", example = "홍길동")
            @Nullable @RequestParam String search,

            @Parameter(description = "페이지 번호", example = "1")
            @RequestParam Integer page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam Integer limit,

            @Parameter(description = "정렬 기준", example = "createdAt")
            @RequestParam String sortBy,

            @Parameter(description = "정렬 방향", example = "desc")
            @RequestParam String sortOrder) {

        AdminSearchCondition searchCondition =
                new AdminSearchCondition(page, limit, sortBy, sortOrder, status, role, search);

        AdminPageResponse resBody = adminService.getAdminsWithCondition(searchCondition);

        return ResponseEntity
                .status(SuccessCode.ADMIN_LIST_SELECT_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_LIST_SELECT_SUCCESS, resBody));
    }

    @Operation(
            summary = "관리자 상세 조회",
            description = "관리자 ID를 이용하여 관리자 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "관리자를 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ADMIN_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "ADMIN NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<AdminDetailResponse>> getAdminById(
            @Parameter(description = "관리자 ID", example = "1", required = true)
            @PathVariable Long id) {

        AdminDetailResponse resBody = adminService.getAdminDetail(id);

        return ResponseEntity
                .status(SuccessCode.ADMIN_DETAIL_SELECT_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_DETAIL_SELECT_SUCCESS, resBody));
    }
}