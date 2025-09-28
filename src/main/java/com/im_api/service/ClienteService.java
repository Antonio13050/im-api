package com.im_api.service;

import com.im_api.dto.ClienteDTO;
import com.im_api.model.Cliente;
import com.im_api.model.User;
import com.im_api.model.enums.Perfil;
import com.im_api.repository.ClienteRepository;
import com.im_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;

    public ClienteService(ClienteRepository clienteRepository, UserRepository userRepository) {
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
    }

    public Cliente create(ClienteDTO clienteDTO) {
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

        System.out.println("Criando cliente com ClienteDTO: " + clienteDTO);
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
        cliente.setCorretorId(clienteDTO.getCorretorId());
        cliente.setPerfil(Perfil.valueOf(clienteDTO.getPerfil().toUpperCase()));
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setInteresses(clienteDTO.getInteresses());
        cliente.setObservacoes(clienteDTO.getObservacoes());

        Cliente saved = clienteRepository.save(cliente);
        System.out.println("Cliente salvo: " + saved);
        return saved;
    }

    public Cliente update(Long id, ClienteDTO clienteDTO) {
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

        if (!currentUserRole.equals("ADMIN") && !cliente.getCorretorId().equals(Long.parseLong(currentUserId))) {
            throw new IllegalArgumentException("Você não tem permissão para atualizar este cliente");
        }

        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
        cliente.setDataNascimento(clienteDTO.getDataNascimento());
        cliente.setCorretorId(clienteDTO.getCorretorId());
        cliente.setPerfil(Perfil.valueOf(clienteDTO.getPerfil().toUpperCase()));
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setInteresses(clienteDTO.getInteresses());
        cliente.setObservacoes(clienteDTO.getObservacoes());

        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));

        if (currentUserRole.equals("ADMIN")) {
            return clienteRepository.findAll();
        } else if (currentUserRole.equals("GERENTE")) {
            Long gerenteId = Long.parseLong(currentUserId);
            List<User> teamUsers = userRepository.findByGerenteId(gerenteId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(gerenteId);
            System.out.println("Team IDs: " + teamIds);
            return clienteRepository.findByCorretorIdIn(teamIds);
        } else {
            Long corretorId = Long.parseLong(currentUserId);
            return clienteRepository.findByCorretorId(corretorId);
        }
    }

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

        if (!currentUserRole.equals("ADMIN") && !cliente.getCorretorId().equals(Long.parseLong(currentUserId))) {
            throw new IllegalArgumentException("Você não tem permissão para excluir este cliente");
        }

        clienteRepository.delete(cliente);
    }
}