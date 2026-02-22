package com.im_api.controller;

import com.im_api.dto.ClienteRequestDTO;
import com.im_api.dto.ClienteResponseDTO;
import com.im_api.dto.ClienteFilterDTO;
import com.im_api.model.Cliente;
import com.im_api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
        ClienteResponseDTO clienteDTO = clienteService.findById(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> findAll(
            @ModelAttribute ClienteFilterDTO filters,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ClienteResponseDTO> clientes = clienteService.findAllPaged(pageable, filters);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(
            @RequestPart(value = "documentos", required = false) List<MultipartFile> documentos,
            @Valid @RequestPart("cliente") ClienteRequestDTO clienteDTO) throws IOException {
        ClienteResponseDTO responseDTO = clienteService.create(clienteDTO, documentos);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(
            @PathVariable Long id,
            @RequestPart(value = "documentos", required = false) List<MultipartFile> documentos,
            @Valid @RequestPart("cliente") ClienteRequestDTO clienteDTO) throws IOException {
        ClienteResponseDTO responseDTO = clienteService.update(id, clienteDTO, documentos);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

