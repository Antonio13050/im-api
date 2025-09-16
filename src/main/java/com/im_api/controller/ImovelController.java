package com.im_api.controller;

import com.im_api.dto.ImovelDTO;
import com.im_api.model.Imovel;
import com.im_api.service.ImovelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/imoveis")
@CrossOrigin
public class ImovelController {

    private final ImovelService imovelService;

    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ImovelDTO>> findAll() {
        List<ImovelDTO> imoveis = imovelService.findAll()
                .stream()
                .map(ImovelDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(imoveis);
    }

    @PostMapping
    public ResponseEntity<ImovelDTO> create(
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos,
            @RequestPart("imovel") ImovelDTO imovelDTO) throws IOException {
        Imovel savedImovel = imovelService.create(imovelDTO, fotos);
        ImovelDTO responseDTO = new ImovelDTO(savedImovel); // Convert to DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ImovelDTO> update(
            @PathVariable Long id,
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos,
            @RequestPart("imovel") ImovelDTO imovelDTO) throws IOException {
        Imovel updatedImovel = imovelService.update(id, imovelDTO, fotos);
        ImovelDTO responseDTO = new ImovelDTO(updatedImovel);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imovelService.delete(id);
        return ResponseEntity.noContent().build();
    }

}