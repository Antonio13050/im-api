package com.im_api.service;

import com.im_api.dto.UpdateStatusRequestDTO;
import com.im_api.model.Processo;
import com.im_api.model.ProcessoStatusHistory;
import com.im_api.model.User;
import com.im_api.repository.ProcessoRepository;
import com.im_api.repository.ProcessoStatusHistoryRepository;
import com.im_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final ProcessoStatusHistoryRepository historyRepository;

    public ProcessoService(ProcessoRepository processoRepository, UserRepository userRepository, ProcessoStatusHistoryRepository historyRepository) {
        this.processoRepository = processoRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    public List<Processo> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));

        if (currentUserRole.equals("ADMIN")) {
            return processoRepository.findAll();
        } else if (currentUserRole.equals("GERENTE")) {
            Long gerenteId = Long.parseLong(currentUserId);

            List<User> teamUsers = userRepository.findByGerenteId(gerenteId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(gerenteId);
            return processoRepository.findByCorretorIdIn(teamIds);
        }
        Long corretorId = Long.parseLong(currentUserId);
        return processoRepository.findByCorretorId(corretorId);
    }

    public Processo create(Processo processo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        Long corretorId = Long.parseLong(currentUserId);
        processo.setCorretorId(corretorId);
        Processo saved = processoRepository.save(processo);
        historyRepository.save(new ProcessoStatusHistory(
                saved.getId(),
                null,
                saved.getStatus(),
                "Processo criado",
                corretorId));
        return saved;
    }

    public Processo updateStatus(Long id, UpdateStatusRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        Long corretorId = Long.parseLong(currentUserId);

        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));
        String oldStatus = processo.getStatus();
        processo.setStatus(request.getStatus());

        Processo updatedProcesso = processoRepository.save(processo);

        historyRepository.save(new ProcessoStatusHistory(
                id,
                oldStatus,
                request.getStatus(),
                request.getObservacao(),
                corretorId));

        return updatedProcesso;
    }

    public Processo update(Long id, Processo updateProcesso) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Processo com ID " + id + " não encontrado"));

        processo.setImovelId(updateProcesso.getImovelId());
        processo.setClienteId(updateProcesso.getClienteId());
        processo.setValorProposto(updateProcesso.getValorProposto());
        processo.setTipoFinanciamento(updateProcesso.getTipoFinanciamento());
        processo.setNomeBanco(updateProcesso.getNomeBanco());
        processo.setObservacoes(updateProcesso.getObservacoes());

        return processoRepository.save(processo);
    }
}