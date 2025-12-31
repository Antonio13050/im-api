package com.im_api.service;

import com.im_api.dto.ClienteRequestDTO;
import com.im_api.dto.ClienteResponseDTO;
import com.im_api.exception.BusinessException;
import com.im_api.mapper.ClienteMapper;
import com.im_api.model.Cliente;
import com.im_api.model.DocumentoCliente;
import com.im_api.model.User;
import com.im_api.repository.ClienteRepository;
import com.im_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, UserRepository userRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));
        return clienteMapper.toDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO clienteDTO, List<MultipartFile> documentos) throws IOException {
        // Validação de negócio: verificar se corretor existe (validações de formato já feitas pelo @Valid)
        if (clienteDTO.getCorretorId() != null && !userRepository.existsById(clienteDTO.getCorretorId())) {
            throw new BusinessException("Corretor com ID " + clienteDTO.getCorretorId() + " não encontrado");
        }

        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        // Processar documentos
        if (documentos != null && !documentos.isEmpty()) {
            for (MultipartFile file : documentos) {
                DocumentoCliente doc = new DocumentoCliente(
                        file.getOriginalFilename(),
                        "outros", // tipo padrão, pode ser atualizado via DTO
                        file.getBytes(),
                        file.getContentType(),
                        file.getSize(),
                        cliente
                );
                cliente.getDocumentos().add(doc);
            }
        }

        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(savedCliente);
    }

    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO clienteDTO, List<MultipartFile> documentos) throws IOException {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));

        // Validação de negócio: verificar se corretor existe (validações de formato já feitas pelo @Valid)
        if (clienteDTO.getCorretorId() != null && !userRepository.existsById(clienteDTO.getCorretorId())) {
            throw new BusinessException("Corretor com ID " + clienteDTO.getCorretorId() + " não encontrado");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));

        if (!currentUserRole.equals("ADMIN") && cliente.getCorretorId() != null &&
                !cliente.getCorretorId().equals(Long.parseLong(currentUserId))) {
            throw new BusinessException("Você não tem permissão para atualizar este cliente");
        }

        // Atualizar campos básicos
        clienteMapper.updateEntityFromDTO(clienteDTO, cliente);

        // Adicionar novos documentos
        if (documentos != null && !documentos.isEmpty()) {
            for (MultipartFile file : documentos) {
                DocumentoCliente doc = new DocumentoCliente(
                        file.getOriginalFilename(),
                        "outros",
                        file.getBytes(),
                        file.getContentType(),
                        file.getSize(),
                        cliente
                );
                cliente.getDocumentos().add(doc);
            }
        }

        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(savedCliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));

        List<Cliente> clientes;
        if (currentUserRole.equals("ADMIN")) {
            clientes = clienteRepository.findAll();
        } else if (currentUserRole.equals("GERENTE")) {
            Long gerenteId = Long.parseLong(currentUserId);
            List<User> teamUsers = userRepository.findByGerenteId(gerenteId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(gerenteId);
            clientes = clienteRepository.findByCorretorIdIn(teamIds);
        } else {
            Long corretorId = Long.parseLong(currentUserId);
            clientes = clienteRepository.findByCorretorId(corretorId);
        }

        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));

        if (!currentUserRole.equals("ADMIN") && cliente.getCorretorId() != null &&
                !cliente.getCorretorId().equals(Long.parseLong(currentUserId))) {
            throw new BusinessException("Você não tem permissão para excluir este cliente");
        }

        clienteRepository.delete(cliente);
    }
}

