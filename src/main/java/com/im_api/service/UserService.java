package com.im_api.service;

import com.im_api.dto.UserCreateDTO;
import com.im_api.model.Role;
import com.im_api.model.User;
import com.im_api.repository.RoleRepository;
import com.im_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserCreateDTO userCreateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));

        if (!currentUserRole.equals("ADMIN") && !currentUserRole.equals("GERENTE")) {
            throw new RuntimeException("Apenas ADMIN ou GERENTE podem criar usuários");
        }

        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já está em uso");
        }

        User user = new User();
        user.setNome(userCreateDTO.getNome());
        user.setEmail(userCreateDTO.getEmail());
        user.setSenha(passwordEncoder.encode(userCreateDTO.getSenha()));
        user.setTelefone(userCreateDTO.getTelefone());
        user.setCreci(userCreateDTO.getCreci());
        user.setAtivo(userCreateDTO.isAtivo());

        if (currentUserRole.equals("GERENTE")) {
            user.setGerenteId(Long.parseLong(currentUserId));
        } else {
            user.setGerenteId(userCreateDTO.getGerenteId());
        }

        String roleName = userCreateDTO.getRole().toUpperCase();
        if (!roleName.equals("CORRETOR") && !roleName.equals("GERENTE")) {
            throw new RuntimeException("Role inválida: deve ser CORRETOR ou GERENTE");
        }
        Role role = roleRepository.findByNome(roleName)
                .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleName));
        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }

    public List<User> findUsersByRole(Long userId, String role) {
        if (role.contains("ADMIN")) {
            return userRepository.findByRoles_NomeIn(List.of("ADMIN", "CORRETOR", "GERENTE"));
        } else if (role.contains("GERENTE")) {
            return userRepository.findByRoles_NomeAndGerenteId("CORRETOR", userId);
        }
        return List.of();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User update(Long userId, UserCreateDTO userUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));

        if (!currentUserRole.equals("ADMIN") && !currentUserRole.equals("GERENTE")) {
            throw new RuntimeException("Apenas ADMIN ou GERENTE podem atualizar usuários");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));

        if (!user.getEmail().equals(userUpdateDTO.getEmail()) &&
                userRepository.findByEmail(userUpdateDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já está em uso");
        }

        user.setNome(userUpdateDTO.getNome());
        user.setEmail(userUpdateDTO.getEmail());
        if (userUpdateDTO.getSenha() != null && !userUpdateDTO.getSenha().isEmpty()) {
            user.setSenha(passwordEncoder.encode(userUpdateDTO.getSenha()));
        }
        user.setTelefone(userUpdateDTO.getTelefone());
        user.setCreci(userUpdateDTO.getCreci());
        user.setAtivo(userUpdateDTO.isAtivo());

        if (currentUserRole.equals("ADMIN")) {
            user.setGerenteId(userUpdateDTO.getGerenteId());
        } else {
            user.setGerenteId(Long.parseLong(authentication.getName()));
        }

        String roleName = userUpdateDTO.getRole().toUpperCase();
        if (!roleName.equals("CORRETOR") && !roleName.equals("GERENTE")) {
            throw new RuntimeException("Role inválida: deve ser CORRETOR ou GERENTE");
        }
        Role role = roleRepository.findByNome(roleName)
                .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleName));

        Set<Role> newRoles = new HashSet<>();
        newRoles.add(role);
        user.setRoles(newRoles);

        System.out.println("Updating user: " + userId + ", new role: " + roleName + ", gerenteId: " + userUpdateDTO.getGerenteId());

        return userRepository.save(user);
    }
}
