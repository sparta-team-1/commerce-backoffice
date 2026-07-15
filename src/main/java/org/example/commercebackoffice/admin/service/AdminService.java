package org.example.commercebackoffice.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminEditRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminResponse;
import org.example.commercebackoffice.admin.controller.auth.SessionUser;
import org.example.commercebackoffice.admin.controller.auth.dto.request.LoginRequest;
import org.example.commercebackoffice.admin.controller.auth.dto.request.SignupRequest;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.repository.AdminRepository;
import org.example.commercebackoffice.config.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//중복체크 -> 암호화 ->저장

@Service
@RequiredArgsConstructor
public class AdminService {
    private  final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder; //암호화 부품

    @Transactional
    public void signup(SignupRequest request) {
        //이메일 중복 여부 확인
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        //엔티티 생성
        //admin.of -> 내부에서 status는 자동으로 PENDING, CREATEDAT은  JPA auditing(@CreatedDate)에 의해 저장 시점에 자동으로 채워진다.
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .status(AdminStatus.PENDING)
                .build();


//                request.getName(),
//                request.getEmail(),
//                encodedPassword, //원래 비밀번호 대신 암호문 넣기
//                request.getPhoneNumber(),
//                request.getRole(),
//                "PENDING",
//                LocalDateTime.now() //요구사항: 자동으로 현재 시간 설정

        adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    //로그인 로직
    public SessionUser login(LoginRequest loginRequest) {
        //관리자 계정 존재 여부 확인
        Admin found = adminRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        //비밀번호 확인
        if(!found.verifyPassword(loginRequest.password(), passwordEncoder))
            throw new RuntimeException("비밀번호 불일치");

        //해당 사용자 정보를 세션에 저장할 DTO로 매핑
        return AdminMapper.toSessionUser(found);
    }

    //관리자 계정 수정 로직
    @Transactional
    public AdminResponse editAdminInfo(Long curAdminId, Long id, AdminEditRequest editRequest) {
        Admin found = findById(id);

        //지금 계정이 SUPER 관리자 계정인지 확인
        chkSuperAdmin(curAdminId);

        //null값이 들어오면 변경하지 않음
        if(editRequest.name() != null) {
            found.changeName(editRequest.name());
        }
        if(editRequest.email() != null) {
            found.changeEmail(editRequest.email());
        }
        if(editRequest.phone() != null) {
            found.changePhone(editRequest.phone());
        }

        Admin saved = adminRepository.save(found);

        //매퍼로 매핑
        return AdminMapper.toAdminResponse(saved);
    }

    //id로 관리자 계정을 찾음
    private Admin findById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("관리자 계정을 찾을 수 없습니다."));
    }

    //SUPER 관리자 계정인지 확인
    private void chkSuperAdmin(Long adminId) {
        Admin found = findById(adminId);

        if(found.getRole() != AdminRole.SUPER_ADMIN)
            throw new RuntimeException("해당 계정은 SUPER 관리가 계정이 아닙니다.");
    }
}
