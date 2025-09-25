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

@Service
public class AuthService {
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

        String role = user.getRoles().stream()
                .map(Role::getNome)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum papel associado ao usuário"));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(java.time.Instant.now())
                .expiresAt(java.time.Instant.now().plusSeconds(86400))
                .subject(user.getUserId().toString())
                .claim("scope", role)
                .claim("email", user.getEmail())
                .claim("nome", user.getNome())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponseDTO(token);
    }
}