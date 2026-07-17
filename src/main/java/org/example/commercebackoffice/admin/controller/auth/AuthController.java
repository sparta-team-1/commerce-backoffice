package org.example.commercebackoffice.admin.controller.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.auth.dto.request.LoginRequest;
import org.example.commercebackoffice.admin.controller.auth.dto.request.SignupRequest;
import org.example.commercebackoffice.admin.service.AdminService;
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
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        adminService.signup(request);

        return  ResponseEntity.ok("회원가입 신청이 완료되었습니다. 승인을 기다려주세요");
    }

    //테스트용 슈퍼 관리자 추가
    @PostMapping("/register/super")
    public ResponseEntity<?> superAdminRegister(@Valid @RequestBody SignupRequest request) {
        adminService.signup(request);

        return  ResponseEntity.ok("회원가입 신청이 완료되었습니다. 승인을 기다려주세요");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        SessionUser sessionUser = adminService.login(loginRequest);

        //세션 정보 저장, 만료 기간 설정(1시간)
        session.setAttribute("userInfo", sessionUser);
        session.setMaxInactiveInterval(SESSION_EXPIRATION);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();

        //헤더의 cache 비우고 재검증 요구 및 즉시 만료
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);

        //JSESSIONID 쿠키 값 null로 설정 및 즉시 만료
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .httpOnly(true)
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
