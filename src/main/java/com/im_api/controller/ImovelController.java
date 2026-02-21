package com.im_api.controller;

import com.im_api.dto.ImovelRequestDTO;
import com.im_api.dto.ImovelResponseDTO;
import com.im_api.dto.ImovelFilterDTO;
import com.im_api.model.Imovel;
import com.im_api.service.ImovelService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/imoveis")
public class ImovelController {
    private final ImovelService imovelService;
    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ImovelResponseDTO> findById(@PathVariable Long id) {
        ImovelResponseDTO imovelDTO = imovelService.findById(id);
        return ResponseEntity.ok(imovelDTO);
    }

//    @GetMapping
//    public ResponseEntity<List<ImovelResponseDTO>> findAll() {
//        List<ImovelResponseDTO> imoveis = imovelService.findAll();
//        return ResponseEntity.ok(imoveis);
//    }

    @GetMapping
    public ResponseEntity<Page<ImovelResponseDTO>> findAll(
            @ModelAttribute ImovelFilterDTO filters,
            @PageableDefault(size = 10, page = 0) final Pageable pageable) {
        Page<ImovelResponseDTO> imoveisPage = imovelService.findAllPaged(pageable, filters);
        return ResponseEntity.ok(imoveisPage);
    }

    @PostMapping
    public ResponseEntity<ImovelResponseDTO> create(
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos,
            @RequestPart(value = "videos", required = false) List<MultipartFile> videos,
            @RequestPart(value = "documentos", required = false) List<MultipartFile> documentos,
            @Valid @RequestPart("imovel") ImovelRequestDTO imovelDTO) throws IOException {

        ImovelResponseDTO responseDTO = imovelService.create(imovelDTO, fotos, videos, documentos);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ImovelResponseDTO> update(
            @PathVariable Long id,
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos,
            @RequestPart(value = "videos", required = false) List<MultipartFile> videos,
            @RequestPart(value = "documentos", required = false) List<MultipartFile> documentos,
            @Valid @RequestPart("imovel") ImovelRequestDTO imovelDTO) throws IOException {

        ImovelResponseDTO responseDTO = imovelService.update(id, imovelDTO, fotos, videos, documentos);
        return ResponseEntity.ok(responseDTO);
    }
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imovelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}