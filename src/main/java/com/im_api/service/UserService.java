package com.im_api.service;

import com.im_api.dto.UserCreateDTO;
import com.im_api.mapper.UserMapper;
import com.im_api.model.Role;
import com.im_api.model.User;
import com.im_api.repository.RoleRepository;
import com.im_api.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
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

        User user = userMapper.toEntity(userCreateDTO);
        user.setSenha(passwordEncoder.encode(userCreateDTO.getSenha()));

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

    @Transactional(readOnly = true)
    public List<User> findUsersByRole(Long userId, String role) {
        if (role.contains("ADMIN")) {
            return userRepository.findByRoles_NomeIn(List.of("ADMIN", "CORRETOR", "GERENTE"));
        } else if (role.contains("GERENTE")) {
            return userRepository.findByRoles_NomeAndGerenteId("CORRETOR", userId);
        }
        return List.of();
    }

    @Transactional
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

        // Atualizar campos básicos via MapStruct
        userMapper.updateEntityFromDTO(userUpdateDTO, user);

        if (userUpdateDTO.getSenha() != null && !userUpdateDTO.getSenha().isEmpty()) {
            user.setSenha(passwordEncoder.encode(userUpdateDTO.getSenha()));
        }

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
