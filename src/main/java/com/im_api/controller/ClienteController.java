package com.im_api.controller;

import com.im_api.dto.ClienteDTO;
import com.im_api.model.Cliente;
import com.im_api.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.findById(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> clientes = clienteService.findAll()
                .stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteDTO> create(
            @RequestPart(value = "documentos", required = false) List<MultipartFile> documentos,
            @RequestPart("cliente") ClienteDTO clienteDTO) throws IOException {
        Cliente savedCliente = clienteService.create(clienteDTO, documentos);
        ClienteDTO responseDTO = new ClienteDTO(savedCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteDTO> update(
            @PathVariable Long id,
            @RequestPart(value = "documentos", required = false) List<MultipartFile> documentos,
            @RequestPart("cliente") ClienteDTO clienteDTO) throws IOException {
        Cliente updatedCliente = clienteService.update(id, clienteDTO, documentos);
        ClienteDTO responseDTO = new ClienteDTO(updatedCliente);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

