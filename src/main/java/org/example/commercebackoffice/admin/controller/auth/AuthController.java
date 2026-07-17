package org.example.commercebackoffice.admin.controller.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.auth.dto.request.LoginRequest;
import org.example.commercebackoffice.admin.controller.auth.dto.request.SignupRequest;
import org.example.commercebackoffice.admin.service.AdminService;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") //이 클래스의 모든 메서드는 이걸로 시작
@RequiredArgsConstructor //final이 붙은 서비스 객체를 생성자로 자동으로 주입
public class AuthController {
    private final AdminService adminService;
    private final static int SESSION_EXPIRATION = 3600;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignupRequest request) {
        adminService.signup(request);

        return ResponseEntity
                .status(SuccessCode.ADMIN_SIGNUP_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_SIGNUP_SUCCESS, null));
    }

    //테스트용 슈퍼 관리자 추가
    @PostMapping("/register/super")
    public ResponseEntity<ApiResponse<Void>> superAdminSignup(@Valid @RequestBody SignupRequest request) {
        adminService.superAdminSignup(request);

        // 🟢 TEST_SUPER_ADMIN_CREATE_SUCCESS ("테스트용 슈퍼 관리자 계정이 추가되었습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.TEST_SUPER_ADMIN_CREATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.TEST_SUPER_ADMIN_CREATE_SUCCESS, null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        SessionUser sessionUser = adminService.login(loginRequest);

        // 세션 정보 저장, 만료 기간 설정(1시간)
        session.setAttribute("userInfo", sessionUser);
        session.setMaxInactiveInterval(SESSION_EXPIRATION);

        // 🟢 LOGIN_SUCCESS ("로그인에 성공하였습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.LOGIN_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.LOGIN_SUCCESS, null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        session.invalidate();

        // 헤더의 cache 비우고 재검증 요구 및 즉시 만료 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);

        // JSESSIONID 쿠키 값 null로 설정 및 즉시 만료
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .httpOnly(true)
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        // 🟢 LOGOUT_SUCCESS ("로그아웃되었습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.LOGOUT_SUCCESS.getHttpStatus())
                .headers(headers)
                .body(ApiResponse.of(SuccessCode.LOGOUT_SUCCESS, null));
    }
}
