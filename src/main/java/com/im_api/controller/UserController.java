package com.im_api.controller;

import com.im_api.dto.UserCreateDTO;
import com.im_api.dto.UserResponseDTO;
import com.im_api.dto.UserUpdateDTO;
import com.im_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista usuários conforme permissões do usuário logado")
    public ResponseEntity<List<UserResponseDTO>> list(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        List<UserResponseDTO> users = userService.findUsersByRole(userId, role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/paged")
    @Operation(summary = "Listar usuários paginados", description = "Lista todos os usuários com paginação")
    public ResponseEntity<Page<UserResponseDTO>> listPaged(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<UserResponseDTO> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Listar usuários por role", description = "Lista usuários filtrados por role com paginação")
    public ResponseEntity<Page<UserResponseDTO>> listByRole(
            @PathVariable String role,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<UserResponseDTO> users = userService.findByRole(role, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        UserResponseDTO user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário (ADMIN ou GERENTE)")
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO user = userService.create(userCreateDTO);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserResponseDTO user = userService.update(id, userUpdateDTO);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status do usuário", description = "Ativa ou desativa um usuário")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam boolean ativo) {
        userService.updateStatus(id, ativo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário do sistema")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
