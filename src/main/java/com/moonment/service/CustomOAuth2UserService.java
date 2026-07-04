package com.moonment.service;

import com.moonment.entity.User;
import com.moonment.enums.Role;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String socialId = oAuth2User.getAttribute("sub");

        System.out.println("========== Google User ==========");
        System.out.println("email = " + email);
        System.out.println("name = " + name);
        System.out.println("socialId = " + socialId);
        System.out.println("=================================");

        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            System.out.println("기존 회원입니다.");
        } else {
            user = User.builder()
                    .userId(UUID.randomUUID())
                    .email(email)
                    .socialId(socialId)
                    .name(name)
                    .alertEnabled(false)
                    .aiAnalysisEnabled(false)
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
            System.out.println("신규 회원 저장 완료");
        }

        return oAuth2User;
    }
}
