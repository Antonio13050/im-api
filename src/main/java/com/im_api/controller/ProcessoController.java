package com.im_api.controller;

import com.im_api.dto.UpdateStatusRequestDTO;
import com.im_api.model.Processo;
import com.im_api.service.ProcessoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoService processoService;

    public ProcessoController(ProcessoService processoService) {
        this.processoService = processoService;
    }

    @GetMapping
    public ResponseEntity<List<Processo>> listarProcessos() {
        return ResponseEntity.ok(processoService.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Processo> create(@RequestBody Processo processo) {
        Processo savedCliente = processoService.create(processo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    @PutMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Processo> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequestDTO request) {
        try {
            Processo updated = processoService.updateStatus(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Processo> update(@PathVariable Long id,
                                           @RequestBody Processo processo) {
        Processo response = processoService.update(id, processo);
        return ResponseEntity.ok(response);
    }

}