package com.moonment.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.moonment.config.JwtProvider;
import com.moonment.dto.user.GoogleLoginRequest;
import com.moonment.dto.user.LoginResponse;
import com.moonment.entity.User;
import com.moonment.enums.Role;
import com.moonment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleTokenVerifier googleTokenVerifier;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public LoginResponse googleLogin(GoogleLoginRequest request) {

        GoogleIdToken.Payload payload =
                googleTokenVerifier.verify(request.getIdToken());
        String email = payload.getEmail();
        String socialId = payload.getSubject();
        String name = (String) payload.get("name");

        boolean isNewUser = false;

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            isNewUser = true;

            user = User.builder()
                    .userId(UUID.randomUUID())
                    .email(email)
                    //.socialId(socialId)
                    .name(name)
                    .role(Role.USER)
                    .alertEnabled(false)
                    .aiAnalysisEnabled(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
        String jwt = jwtProvider.createToken(email);
        return new LoginResponse(jwt, isNewUser);
    }
}
