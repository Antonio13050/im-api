package com.im_api.service;

import com.im_api.dto.LoginRequestDTO;
import com.im_api.dto.LoginResponseDTO;
import com.im_api.model.Role;
import com.im_api.model.User;
import com.im_api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final long TOKEN_EXPIRATION_SECONDS = 86400;

    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new RuntimeException("Credenciais inválidas");
        }

        if (!user.isAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        Set<String> roles = user.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toSet());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(java.time.Instant.now())
                .expiresAt(java.time.Instant.now().plusSeconds(TOKEN_EXPIRATION_SECONDS))
                .subject(user.getUserId().toString())
                .claim("scope", String.join(" ", roles))
                .claim("email", user.getEmail())
                .claim("nome", user.getNome())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return LoginResponseDTO.builder()
                .token(token)
                .userId(user.getUserId())
                .nome(user.getNome())
                .email(user.getEmail())
                .roles(roles)
                .expiresIn(TOKEN_EXPIRATION_SECONDS)
                .build();
    }
}
