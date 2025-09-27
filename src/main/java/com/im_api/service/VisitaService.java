package com.im_api.service;

import com.im_api.model.Cliente;
import com.im_api.model.Visita;
import com.im_api.repository.ClienteRepository;
import com.im_api.repository.VisitaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    private final VisitaRepository visitaRepository;
    private final ClienteRepository clienteRepository;

    public VisitaService(VisitaRepository visitaRepository, ClienteRepository clienteRepository) {
        this.visitaRepository = visitaRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Visita> getAllVisits() {
        return visitaRepository.findAll();
    }

    public List<Visita> getVisitsByRealtorId(Long corretorId) {
        return visitaRepository.findByCorretorId(corretorId);
    }

    public Optional<Visita> getVisitById(Long id) {
        return visitaRepository.findById(id);
    }

    public Visita create(Visita visit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        Long corretorId = Long.parseLong(currentUserId);
        visit.setCorretorId(corretorId);

        Cliente cliente = clienteRepository.findById(visit.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        visit.setClientName(cliente.getNome());
        visit.setClientEmail(cliente.getEmail());
        visit.setClientPhone(cliente.getTelefone());

        return visitaRepository.save(visit);
    }

    public Visita updateStatus(Long id, String status) {
        Visita visit = visitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não encontrada"));
        visit.setStatus(status);
        return visitaRepository.save(visit);
    }

    public Visita update(Long id, Visita visitDetails) {
        Visita visit = visitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não encontrada"));

        visit.setImovelId(visitDetails.getImovelId());
        visit.setClienteId(visitDetails.getClienteId());
        visit.setClientName(visitDetails.getClientName());
        visit.setClientPhone(visitDetails.getClientPhone());
        visit.setClientEmail(visitDetails.getClientEmail());
        visit.setScheduledDate(visitDetails.getScheduledDate());
        visit.setScheduledTime(visitDetails.getScheduledTime());
        visit.setStatus(visitDetails.getStatus());
        visit.setCorretorId(visitDetails.getCorretorId());
        visit.setNotes(visitDetails.getNotes());
        visit.setClientFeedback(visitDetails.getClientFeedback());
        visit.setInterestLevel(visitDetails.getInterestLevel());
        return visitaRepository.save(visit);
    }

    public void deleteVisit(Long id) {
        visitaRepository.deleteById(id);
    }
}
