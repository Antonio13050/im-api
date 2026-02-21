package com.im_api.service;

import com.im_api.dto.UserCreateDTO;
import com.im_api.dto.UserResponseDTO;
import com.im_api.dto.UserUpdateDTO;
import com.im_api.exception.BusinessException;
import com.im_api.mapper.UserMapper;
import com.im_api.model.Role;
import com.im_api.model.User;
import com.im_api.repository.RoleRepository;
import com.im_api.repository.UserRepository;
import com.im_api.util.SecurityContextUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
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
    private final SecurityContextUtil securityContextUtil;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, 
                       PasswordEncoder passwordEncoder, UserMapper userMapper,
                       SecurityContextUtil securityContextUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.securityContextUtil = securityContextUtil;
    }

    @Transactional
    public UserResponseDTO create(UserCreateDTO userCreateDTO) {
        validateAdminOrGerentePermission("criar usuários");

        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            throw new BusinessException("Email já está em uso");
        }

        if (userCreateDTO.getCpf() != null && !userCreateDTO.getCpf().isBlank()) {
            if (userRepository.findByCpf(userCreateDTO.getCpf()).isPresent()) {
                throw new BusinessException("CPF já está cadastrado");
            }
        }

        validateCreciForRole(userCreateDTO.getRole(), userCreateDTO.getCreci(), null);

        User user = userMapper.toEntity(userCreateDTO);
        user.setSenha(passwordEncoder.encode(userCreateDTO.getSenha()));
        user.setGerenteId(resolveGerenteId(userCreateDTO.getGerenteId()));

        Role role = validateAndFindRole(userCreateDTO.getRole());
        user.setRoles(Collections.singleton(role));

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findByRole(String role, Pageable pageable) {
        return userRepository.findByRoles_Nome(role, pageable)
                .map(userMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findUsersByRole(Long userId, String role) {
        if (role.contains("ADMIN")) {
            return userRepository.findByRoles_NomeIn(List.of("ADMIN", "CORRETOR", "GERENTE", "SECRETARIO"))
                    .stream()
                    .map(userMapper::toResponseDTO)
                    .toList();
        } else if (role.contains("GERENTE")) {
            return userRepository.findByGerenteId(userId)
                    .stream()
                    .map(userMapper::toResponseDTO)
                    .toList();
        }
        return List.of();
    }

    @Transactional
    public UserResponseDTO update(Long userId, UserUpdateDTO userUpdateDTO) {
        validateAdminOrGerentePermission("atualizar usuários");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado: " + userId));

        if (!user.getEmail().equals(userUpdateDTO.getEmail())) {
            if (userRepository.findByEmail(userUpdateDTO.getEmail()).isPresent()) {
                throw new BusinessException("Email já está em uso");
            }
        }

        if (userUpdateDTO.getCpf() != null && !userUpdateDTO.getCpf().isBlank()) {
            if (!userUpdateDTO.getCpf().equals(user.getCpf()) && 
                userRepository.findByCpf(userUpdateDTO.getCpf()).isPresent()) {
                throw new BusinessException("CPF já está cadastrado");
            }
        }

        validateCreciForRole(userUpdateDTO.getRole(), userUpdateDTO.getCreci(), user.getCreci());

        userMapper.updateEntityFromDTO(userUpdateDTO, user);

        if (userUpdateDTO.getSenha() != null && !userUpdateDTO.getSenha().isBlank()) {
            user.setSenha(passwordEncoder.encode(userUpdateDTO.getSenha()));
        }

        user.setGerenteId(resolveGerenteId(userUpdateDTO.getGerenteId()));

        Role role = validateAndFindRole(userUpdateDTO.getRole());
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(role);
        user.setRoles(newRoles);

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional
    public void updateStatus(Long userId, boolean ativo, Long currentUserId) {
        if (userId.equals(currentUserId)) {
            throw new BusinessException("Não é possível alterar o status do próprio usuário");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));
        user.setAtivo(ativo);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id, Long currentUserId) {
        if (id.equals(currentUserId)) {
            throw new BusinessException("Não é possível excluir seu próprio usuário");
        }
        if (!userRepository.existsById(id)) {
            throw new BusinessException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }

    private void validateCreciForRole(String role, String newCreci, String currentCreci) {
        if ("CORRETOR".equalsIgnoreCase(role)) {
            String creci = newCreci != null ? newCreci : currentCreci;
            if (creci == null || creci.isBlank()) {
                throw new BusinessException("CRECI é obrigatório para corretores");
            }
            if (newCreci != null && !newCreci.equals(currentCreci)) {
                if (userRepository.findByCreci(newCreci).isPresent()) {
                    throw new BusinessException("CRECI já está cadastrado");
                }
            }
        }
    }

    private void validateAdminOrGerentePermission(String action) {
        String currentUserRole = securityContextUtil.getCurrentUserRole();
        
        if (!currentUserRole.equals("ADMIN") && !currentUserRole.equals("GERENTE")) {
            throw new BusinessException("Apenas ADMIN ou GERENTE podem " + action);
        }
    }

    private Long resolveGerenteId(Long requestGerenteId) {
        String currentUserRole = securityContextUtil.getCurrentUserRole();
        
        if (currentUserRole.equals("GERENTE")) {
            return securityContextUtil.getCurrentUserIdAsLong();
        }
        return requestGerenteId;
    }

    private Role validateAndFindRole(String roleName) {
        String currentUserRole = securityContextUtil.getCurrentUserRole();
        String normalizedRole = roleName.toUpperCase();

        if (currentUserRole.equals("ADMIN")) {
            if (!normalizedRole.equals("ADMIN") && 
                !normalizedRole.equals("GERENTE") && 
                !normalizedRole.equals("CORRETOR") && 
                !normalizedRole.equals("SECRETARIO")) {
                throw new BusinessException("Role inválida: deve ser ADMIN, GERENTE, CORRETOR ou SECRETARIO");
            }
        } else {
            if (!normalizedRole.equals("CORRETOR")) {
                throw new BusinessException("Gerentes só podem criar usuários com role CORRETOR");
            }
        }
        
        return roleRepository.findByNome(normalizedRole)
                .orElseThrow(() -> new BusinessException("Role não encontrada: " + normalizedRole));
    }
}
