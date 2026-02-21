package com.im_api.service;

import com.im_api.dto.UpdateStatusRequestDTO;
import com.im_api.model.Processo;
import com.im_api.model.ProcessoStatusHistory;
import com.im_api.model.User;
import com.im_api.repository.ProcessoRepository;
import com.im_api.repository.ProcessoStatusHistoryRepository;
import com.im_api.repository.UserRepository;
import com.im_api.util.SecurityContextUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final ProcessoStatusHistoryRepository historyRepository;
    private final SecurityContextUtil securityContextUtil;

    public ProcessoService(ProcessoRepository processoRepository, UserRepository userRepository,
                           ProcessoStatusHistoryRepository historyRepository, SecurityContextUtil securityContextUtil) {
        this.processoRepository = processoRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.securityContextUtil = securityContextUtil;
    }

    @Transactional(readOnly = true)
    public List<Processo> findAll() {
        Long currentUserId = securityContextUtil.getCurrentUserIdAsLong();
        String currentUserRole = securityContextUtil.getCurrentUserRole();

        if (securityContextUtil.isAdmin()) {
            return processoRepository.findAll();
        } else if (securityContextUtil.isGerente()) {
            List<User> teamUsers = userRepository.findByGerenteId(currentUserId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(currentUserId);
            return processoRepository.findByCorretorIdIn(teamIds);
        }
        return processoRepository.findByCorretorId(currentUserId);
    }

    @Transactional
    public Processo create(Processo processo) {
        Long corretorId = securityContextUtil.getCurrentUserIdAsLong();
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

    @Transactional
    public Processo updateStatus(Long id, UpdateStatusRequestDTO request) {
        Long corretorId = securityContextUtil.getCurrentUserIdAsLong();

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

    @Transactional
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
