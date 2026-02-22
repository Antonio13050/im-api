package com.im_api.service;

import com.im_api.dto.ClienteRequestDTO;
import com.im_api.dto.ClienteResponseDTO;
import com.im_api.dto.ClienteFilterDTO;
import com.im_api.exception.BusinessException;
import com.im_api.mapper.ClienteMapper;
import com.im_api.model.Cliente;
import com.im_api.model.DocumentoCliente;
import com.im_api.model.User;
import com.im_api.repository.ClienteRepository;
import com.im_api.repository.UserRepository;
import com.im_api.repository.spec.ClienteSpecification;
import com.im_api.util.SecurityContextUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final SecurityContextUtil securityContextUtil;

    public ClienteService(ClienteRepository clienteRepository, UserRepository userRepository, 
                          ClienteMapper clienteMapper, SecurityContextUtil securityContextUtil) {
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
        this.clienteMapper = clienteMapper;
        this.securityContextUtil = securityContextUtil;
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));
        return clienteMapper.toDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO clienteDTO, List<MultipartFile> documentos) throws IOException {
        if (clienteDTO.getCorretorId() != null && !userRepository.existsById(clienteDTO.getCorretorId())) {
            throw new BusinessException("Corretor com ID " + clienteDTO.getCorretorId() + " não encontrado");
        }

        Cliente cliente = clienteMapper.toEntity(clienteDTO);

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

    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO clienteDTO, List<MultipartFile> documentos) throws IOException {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));

        if (clienteDTO.getCorretorId() != null && !userRepository.existsById(clienteDTO.getCorretorId())) {
            throw new BusinessException("Corretor com ID " + clienteDTO.getCorretorId() + " não encontrado");
        }

        Long currentUserId = securityContextUtil.getCurrentUserIdAsLong();
        String currentUserRole = securityContextUtil.getCurrentUserRole();

        if (!securityContextUtil.isAdmin() && cliente.getCorretorId() != null &&
                !cliente.getCorretorId().equals(currentUserId)) {
            throw new BusinessException("Você não tem permissão para atualizar este cliente");
        }

        clienteMapper.updateEntityFromDTO(clienteDTO, cliente);

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
        Long currentUserId = securityContextUtil.getCurrentUserIdAsLong();
        String currentUserRole = securityContextUtil.getCurrentUserRole();

        List<Cliente> clientes;
        if (securityContextUtil.isAdmin()) {
            clientes = clienteRepository.findAll();
        } else if (securityContextUtil.isGerente()) {
            List<User> teamUsers = userRepository.findByGerenteId(currentUserId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(currentUserId);
            clientes = clienteRepository.findByCorretorIdIn(teamIds);
        } else {
            clientes = clienteRepository.findByCorretorId(currentUserId);
        }

        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> findAllPaged(Pageable pageable, ClienteFilterDTO filters) {
        Long currentUserId = securityContextUtil.getCurrentUserIdAsLong();
        
        Specification<Cliente> spec = ClienteSpecification.withFilters(filters);

        // Add scope filter based on user role
        if (securityContextUtil.isGerente()) {
            List<User> teamUsers = userRepository.findByGerenteId(currentUserId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(currentUserId);
            
            Specification<Cliente> scopeSpec = (root, query, cb) -> root.get("corretorId").in(teamIds);
            spec = spec.and(scopeSpec);
        } else if (!securityContextUtil.isAdmin()) {
            Specification<Cliente> scopeSpec = (root, query, cb) -> cb.equal(root.get("corretorId"), currentUserId);
            spec = spec.and(scopeSpec);
        }

        Page<Cliente> clientesPage = clienteRepository.findAll(spec, pageable);
        return clientesPage.map(clienteMapper::toDTO);
    }

    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));

        Long currentUserId = securityContextUtil.getCurrentUserIdAsLong();

        if (!securityContextUtil.isAdmin() && cliente.getCorretorId() != null &&
                !cliente.getCorretorId().equals(currentUserId)) {
            throw new BusinessException("Você não tem permissão para excluir este cliente");
        }

        clienteRepository.delete(cliente);
    }
}
