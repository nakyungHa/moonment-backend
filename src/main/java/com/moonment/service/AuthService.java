package com.moonment.service;

import com.moonment.dto.auth.SignupRequest;
import com.moonment.dto.auth.SignupResponse;
import com.moonment.entity.Streak;
import com.moonment.entity.Terms;
import com.moonment.entity.TermsAgreement;
import com.moonment.entity.User;
import com.moonment.enums.Role;
import com.moonment.repository.StreakRepository;
import com.moonment.repository.TermsAgreementRepository;
import com.moonment.repository.TermsRepository;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final TermsAgreementRepository termsAgreementRepository;
    private final StreakRepository streakRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isLoginIdAvailable(String loginId) {
        return !userRepository.existsByLoginId(loginId);
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        // 중복 확인하기
        // 1. 아이디 중복
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 2. 이메일 중복
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 3. 비밀번호 확인
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 4. 필수 약관
        if (!Boolean.TRUE.equals(request.getServiceTermsAgreed())
                || !Boolean.TRUE.equals(request.getPrivacyTermsAgreed())
                || !Boolean.TRUE.equals(request.getAiAnalysisTermsAgreed())) {

            throw new IllegalArgumentException("필수 약관에 동의해야 합니다.");
        }

        // 신규 User 객체 생성, 저장
        User user = User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getName())
                .sex(request.getSex())
                .goal(request.getGoal())
                .recordTime(request.getRecordTime())
                .alertEnabled(true)
                .aiAnalysisEnabled(true)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        // 신규 스트릭 생성
        Streak streak = Streak.builder()
                .user(user)
                .streakCount(0)
                .build();
        streakRepository.save(streak);

        // 약관 동의 저장
        saveTermsAgreement(user, "service-v1.0");
        saveTermsAgreement(user, "privacy-v1.0");
        saveTermsAgreement(user, "ai-analysis-v1.0");

        return new SignupResponse(
                user.getUserId(),
                "회원가입이 완료되었습니다."
        );
    }

    private void saveTermsAgreement(User user, String version) {
        Terms terms = termsRepository.findByVersion(version)
                .orElseThrow(() -> new IllegalArgumentException("해당 버전의 약관이 없습니다."));

        TermsAgreement agreement = TermsAgreement.builder()
                .user(user)
                .terms(terms)
                .agreedAt(LocalDateTime.now())
                .build();
        termsAgreementRepository.save(agreement);
    }

}
