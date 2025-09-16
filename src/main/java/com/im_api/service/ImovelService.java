package com.im_api.service;

import com.im_api.dto.ImovelDTO;
import com.im_api.model.Foto;
import com.im_api.dto.FotoDTO;
import com.im_api.model.Imovel;
import com.im_api.repository.ImovelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ImovelService {

    private final ImovelRepository imovelRepository;

    public ImovelService(ImovelRepository imovelRepository) {
        this.imovelRepository = imovelRepository;
    }

    public Imovel create(ImovelDTO imovelDTO, List<MultipartFile> fotos) throws IOException {
        Imovel imovel = new Imovel(imovelDTO);

        // Check if fotos is not null and not empty
        if (fotos != null && !fotos.isEmpty()) {
            for (MultipartFile file : fotos) {
                Foto foto = new Foto(
                        file.getOriginalFilename(),
                        file.getBytes(),
                        file.getContentType(),
                        imovel
                );
                imovel.getFotos().add(foto);
            }
        }

        return imovelRepository.save(imovel);
    }

    public Imovel update(Long id, ImovelDTO imovelDTO, List<MultipartFile> fotos) throws IOException {
        // Validate required fields
        if (imovelDTO.getTitulo() == null || imovelDTO.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O título do imóvel é obrigatório");
        }
        if (imovelDTO.getPreco() == null) {
            throw new IllegalArgumentException("O preço do imóvel é obrigatório");
        }
        if (imovelDTO.getEndereco() == null) {
            throw new IllegalArgumentException("O endereço do imóvel é obrigatório");
        }

        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));

        // Update fields
        imovel.setTitulo(imovelDTO.getTitulo());
        imovel.setDescricao(imovelDTO.getDescricao());
        imovel.setTipo(imovelDTO.getTipo());
        imovel.setFinalidade(imovelDTO.getFinalidade());
        imovel.setStatus(imovelDTO.getStatus());
        imovel.setEndereco(imovelDTO.getEndereco());
        imovel.setPreco(imovelDTO.getPreco());
        imovel.setArea(imovelDTO.getArea());
        imovel.setQuartos(imovelDTO.getQuartos());
        imovel.setBanheiros(imovelDTO.getBanheiros());
        imovel.setVagas(imovelDTO.getVagas());
        imovel.setClienteId(imovelDTO.getClienteId());
        imovel.setCorretorId(imovelDTO.getCorretorId());

        // Handle photos: clear existing photos if a list of IDs to keep is provided
        if (imovelDTO.getFotos() != null) {
            List<Long> keepPhotoIds = imovelDTO.getFotos().stream()
                    .map(FotoDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();
            imovel.getFotos().removeIf(foto -> !keepPhotoIds.contains(foto.getId()));
        }

        // Add new photos
        if (fotos != null && !fotos.isEmpty()) {
            for (MultipartFile file : fotos) {
                Foto foto = new Foto(
                        file.getOriginalFilename(),
                        file.getBytes(),
                        file.getContentType(),
                        imovel
                );
                imovel.getFotos().add(foto);
            }
        }

        return imovelRepository.save(imovel);
    }

    public List<Imovel> findAll() {
        return imovelRepository.findAll();
    }

    public void delete(Long id) {
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));
        imovelRepository.delete(imovel);
    }
}
