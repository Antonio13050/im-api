package com.im_api.service;

import com.im_api.dto.ClienteDTO;
import com.im_api.dto.DocumentoClienteDTO;
import com.im_api.mapper.ClienteMapper;
import com.im_api.model.Cliente;
import com.im_api.model.DocumentoCliente;
import com.im_api.model.User;
import com.im_api.model.enums.Perfil;
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
import java.util.Objects;
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
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));
        return clienteMapper.toDTO(cliente);
    }

    @Transactional
    public Cliente create(ClienteDTO clienteDTO, List<MultipartFile> documentos) throws IOException {
        if (clienteDTO.getNome() == null || clienteDTO.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório");
        }

        if (clienteDTO.getCorretorId() != null && !userRepository.existsById(clienteDTO.getCorretorId())) {
            throw new IllegalArgumentException("Corretor com ID " + clienteDTO.getCorretorId() + " não encontrado");
        }

        if (clienteDTO.getPerfil() == null || clienteDTO.getPerfil().isBlank()) {
            throw new IllegalArgumentException("O perfil do cliente é obrigatório");
        }

        try {
            Perfil.valueOf(clienteDTO.getPerfil().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Perfil inválido: " + clienteDTO.getPerfil());
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

        // Atualizar tipos de documentos se fornecidos no DTO
        if (clienteDTO.getDocumentos() != null && !clienteDTO.getDocumentos().isEmpty()) {
            int index = 0;
            for (DocumentoClienteDTO docDTO : clienteDTO.getDocumentos()) {
                if (docDTO.getId() == null && index < cliente.getDocumentos().size()) {
                    cliente.getDocumentos().get(index).setTipoDocumento(docDTO.getTipoDocumento());
                    index++;
                }
            }
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update(Long id, ClienteDTO clienteDTO, List<MultipartFile> documentos) throws IOException {
        if (clienteDTO.getNome() == null || clienteDTO.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório");
        }

        if (clienteDTO.getCorretorId() != null && !userRepository.existsById(clienteDTO.getCorretorId())) {
            throw new IllegalArgumentException("Corretor com ID " + clienteDTO.getCorretorId() + " não encontrado");
        }

        if (clienteDTO.getPerfil() == null || clienteDTO.getPerfil().isBlank()) {
            throw new IllegalArgumentException("O perfil do cliente é obrigatório");
        }

        try {
            Perfil.valueOf(clienteDTO.getPerfil().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Perfil inválido: " + clienteDTO.getPerfil());
        }

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
            throw new IllegalArgumentException("Você não tem permissão para atualizar este cliente");
        }

        // Atualizar campos básicos
        // Atualizar campos básicos via MapStruct
        clienteMapper.updateEntityFromDTO(clienteDTO, cliente);

        // Manter documentos existentes
        if (clienteDTO.getDocumentos() != null) {
            List<Long> keepDocIds = clienteDTO.getDocumentos().stream()
                    .map(DocumentoClienteDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();

            cliente.getDocumentos().removeIf(doc -> !keepDocIds.contains(doc.getId()));

            // Atualizar tipos de documentos existentes
            for (DocumentoClienteDTO docDTO : clienteDTO.getDocumentos()) {
                if (docDTO.getId() != null) {
                    cliente.getDocumentos().stream()
                            .filter(doc -> doc.getId().equals(docDTO.getId()))
                            .findFirst()
                            .ifPresent(doc -> doc.setTipoDocumento(docDTO.getTipoDocumento()));
                }
            }
        }

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

        // Atualizar tipos de novos documentos se fornecidos
        if (clienteDTO.getDocumentos() != null && !clienteDTO.getDocumentos().isEmpty()) {
            List<DocumentoClienteDTO> newDocs = clienteDTO.getDocumentos().stream()
                    .filter(doc -> doc.getId() == null)
                    .collect(Collectors.toList());

            int newDocIndex = 0;
            for (DocumentoCliente doc : cliente.getDocumentos()) {
                if (doc.getId() == null && newDocIndex < newDocs.size()) {
                    doc.setTipoDocumento(newDocs.get(newDocIndex).getTipoDocumento());
                    newDocIndex++;
                }
            }
        }

        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
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
            throw new IllegalArgumentException("Você não tem permissão para excluir este cliente");
        }

        clienteRepository.delete(cliente);
    }
}

