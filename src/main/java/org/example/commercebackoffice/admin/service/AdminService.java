package org.example.commercebackoffice.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminChangePasswordRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminEditRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.request.AdminRejectRequest;
import org.example.commercebackoffice.admin.controller.admin.dto.response.AdminResponse;
import org.example.commercebackoffice.admin.controller.auth.SessionUser;
import org.example.commercebackoffice.admin.controller.auth.dto.request.LoginRequest;
import org.example.commercebackoffice.admin.controller.auth.dto.request.SignupRequest;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;
import org.example.commercebackoffice.admin.repository.AdminRepository;
import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;
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
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!found.isStatusActive())
            throw new CustomException(ErrorCode.USER_NOT_ACTIVE);

        //비밀번호 확인
        if(!found.verifyPassword(loginRequest.password(), passwordEncoder))
            throw new CustomException(ErrorCode.PASSWORD_INCORRECT);

        //해당 사용자 정보를 세션에 저장할 DTO로 매핑
        return AdminMapper.toSessionUser(found);
    }

    //사용자 본인 계정 정보 확인메로직
    @Transactional(readOnly = true)
    public AdminResponse me(Long id) {
        Admin found = findById(id);

        return AdminMapper.toAdminResponse(found);
    }

    //관리자 계정 수정 로직
    @Transactional
    public AdminResponse editAdminInfo(Long curAdminId, Long id, AdminEditRequest editRequest) {
        //지금 계정이 SUPER 관리자 계정인지 확인
        chkSuperAdmin(curAdminId);

        Admin found = findById(id);
        editAdmin(found, editRequest);

        Admin saved = adminRepository.save(found);

        //매퍼로 매핑
        return AdminMapper.toAdminResponse(saved);
    }

    //관리자 역할 변경 로직
    @Transactional
    public AdminResponse changeAdminRole(Long curAdminId, Long id, String role) {
        //지금 계정이 SUPER 관리자 계정인지 확인
        chkSuperAdmin(curAdminId);

        Admin found = findById(id);

                //찾은 관리자 계정 역할 변경
        found.changeRole(AdminRole.from(role));

        Admin saved = adminRepository.save(found);
        return AdminMapper.toAdminResponse(saved);
    }

    //관리자 상태 변경 로직
    @Transactional
    public AdminResponse changeAdminStatus(Long curAdminId, Long id, String status) {
        //지금 계정이 SUPER 관리자 계정인지 확인
        chkSuperAdmin(curAdminId);

        Admin found = findById(id);

        //찾은 관리자 계정 상태 변경
        found.changeStatus(AdminStatus.from(status));

        Admin saved = adminRepository.save(found);
        return AdminMapper.toAdminResponse(saved);
    }

    //관리자 계정 삭제 로직
    @Transactional
    public void deleteAdmin(Long curAdminId, Long id) {
        //지금 계정이 SUPER 관리자 계정인지 확인
        chkSuperAdmin(curAdminId);

        //도메인에서 삭제 로직 진행
        Admin found = findById(id);
        found.delete();

        adminRepository.save(found);
    }

    //관리자 계정 승인 로직
    @Transactional
    public void approveAdmin(Long curAdminId, Long id) {
        //지금 계정이 SUPER 관리자 계정인지 확인
        chkSuperAdmin(curAdminId);

        Admin found = findById(id);

        //대기 상태가 아니면 예외 발생
        if(found.getStatus() != AdminStatus.PENDING) {
            throw new CustomException(ErrorCode.USER_STATUS_NOT_PENDING);
        }
        found.approve();

        adminRepository.save(found);
    }

    //관리자 계정 거부 로직
    @Transactional
    public void rejectAdmin(Long curAdminId, Long id, AdminRejectRequest rejectRequest) {
        //지금 계정이 SUPER 관리자 계정인지 확인
        chkSuperAdmin(curAdminId);

        Admin found = findById(id);

        //대기 상태가 아니면 예외 발생
        if(found.getStatus() != AdminStatus.PENDING) {
            throw new CustomException(ErrorCode.USER_STATUS_NOT_PENDING);
        }
        found.reject(rejectRequest.rejectionMessage());
        adminRepository.save(found);
    }

    //현재 관리자 계정 정보 수정 로직
    @Transactional
    public AdminResponse editCurrentAdminInfo(Long curAdminId, AdminEditRequest editRequest) {
        Admin found = findById(curAdminId);
        editAdmin(found, editRequest);

        Admin saved = adminRepository.save(found);
        return AdminMapper.toAdminResponse(saved);
    }

    //현재 관리자 계정 비밀번호 변경 로직
    @Transactional
    public AdminResponse changeCurrentAdminPassword(Long curAdminId, AdminChangePasswordRequest changePasswordRequest) {
        Admin found = findById(curAdminId);

        //도메인에서 비밀번호 변경
        found.changePassword(changePasswordRequest.password(),  passwordEncoder);
        Admin saved = adminRepository.save(found);

        return AdminMapper.toAdminResponse(saved);
    }

    //id로 관리자 계정을 찾음
    private Admin findById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    //SUPER 관리자 계정인지 확인 후 해당 관리자 계정 반환
    private void chkSuperAdmin(Long adminId) {
        Admin found = findById(adminId);

        if(!found.isSuperAdmin())
            throw new CustomException(ErrorCode.USER_IS_NOT_SUPER_ADMIN);
    }

    //관리자 계정 정보 수정
    private void editAdmin(Admin admin, AdminEditRequest editRequest) {
        //null이면 해당 필드 무시
        if(editRequest.name() != null) {
            admin.changeName(editRequest.name());
        }
        if(editRequest.email() != null) {
            admin.changeEmail(editRequest.email());
        }
        if(editRequest.phone() != null) {
            admin.changePhone(editRequest.phone());
        }
    }
}
