package com.im_api.controller;

import com.im_api.dto.UpdateStatusRequestDTO;
import com.im_api.model.Visita;
import com.im_api.service.VisitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitas")
public class VisitaController {

    private final VisitaService visitaService;

    public VisitaController(VisitaService visitaService) {
        this.visitaService = visitaService;
    }

    @GetMapping
    public ResponseEntity<List<Visita>> getVisits() {
        return ResponseEntity.ok(visitaService.getAllVisits());
    }

    @PostMapping
    public ResponseEntity<Visita> createVisit(@RequestBody Visita visit) {
        return ResponseEntity.ok(visitaService.create(visit));
    }

    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Visita> updateVisitStatus(@PathVariable Long id, @RequestBody UpdateStatusRequestDTO request) {
        try {
            Visita updated = visitaService.updateStatus(id, request.getStatus());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visita> updateVisit(@PathVariable Long id, @RequestBody Visita visitDetails) {
        return ResponseEntity.ok(visitaService.update(id, visitDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitaService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }
}
