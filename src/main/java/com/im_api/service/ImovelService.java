package com.im_api.service;

import com.im_api.dto.ImovelRequestDTO;
import com.im_api.dto.ImovelResponseDTO;
import com.im_api.dto.FotoDTO;
import com.im_api.dto.VideoDTO;
import com.im_api.dto.DocumentoImovelDTO;
import com.im_api.mapper.ImovelMapper;
import com.im_api.model.*;
import com.im_api.repository.ImovelRepository;
import com.im_api.repository.UserRepository;
import com.im_api.util.SecurityContextUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.im_api.dto.ImovelFilterDTO;
import com.im_api.repository.spec.ImovelSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final UserRepository userRepository;
    private final ImovelMapper imovelMapper;
    private final SecurityContextUtil securityContextUtil;

    public ImovelService(ImovelRepository imovelRepository, UserRepository userRepository, 
                         ImovelMapper imovelMapper, SecurityContextUtil securityContextUtil) {
        this.imovelRepository = imovelRepository;
        this.userRepository = userRepository;
        this.imovelMapper = imovelMapper;
        this.securityContextUtil = securityContextUtil;
    }

    private String gerarCodigoImovel() {
        Long count = imovelRepository.count() + 1;
        return String.format("IMV-%05d", count);
    }

    @Transactional(readOnly = true)
    public ImovelResponseDTO findById(Long id) {
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));
        return imovelMapper.toDTO(imovel);
    }

    @Transactional
    public ImovelResponseDTO create(ImovelRequestDTO imovelDTO,
                         List<MultipartFile> fotos,
                         List<MultipartFile> videos,
                         List<MultipartFile> documentos) throws IOException {

        Imovel imovel = imovelMapper.toEntity(imovelDTO);

        if (imovel.getCodigo() == null || imovel.getCodigo().isBlank()) {
            imovel.setCodigo(gerarCodigoImovel());
        }

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

        if (videos != null && !videos.isEmpty()) {
            for (MultipartFile file : videos) {
                Video video = new Video(
                        file.getOriginalFilename(),
                        file.getBytes(),
                        file.getContentType(),
                        file.getSize(),
                        imovel
                );
                imovel.getVideos().add(video);
            }
        }

        if (documentos != null && !documentos.isEmpty()) {
            for (MultipartFile file : documentos) {
                DocumentoImovel doc = new DocumentoImovel(
                        file.getOriginalFilename(),
                        "outros",
                        file.getBytes(),
                        file.getContentType(),
                        file.getSize(),
                        imovel
                );
                imovel.getDocumentos().add(doc);
            }
        }

        Imovel savedImovel = imovelRepository.save(imovel);
        return imovelMapper.toDTO(savedImovel);
    }

    @Transactional
    public ImovelResponseDTO update(Long id, ImovelRequestDTO dto,
                         List<MultipartFile> fotos,
                         List<MultipartFile> videos,
                         List<MultipartFile> documentos) throws IOException {

        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));

        imovelMapper.updateEntityFromDTO(dto, imovel);

        if (dto.getFotos() != null) {
            List<Long> keepPhotoIds = dto.getFotos().stream()
                    .map(FotoDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();
            imovel.getFotos().removeIf(foto -> !keepPhotoIds.contains(foto.getId()));
        }

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

        if (dto.getVideos() != null) {
            List<Long> keepVideoIds = dto.getVideos().stream()
                    .map(VideoDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();
            imovel.getVideos().removeIf(video -> !keepVideoIds.contains(video.getId()));
        }

        if (videos != null && !videos.isEmpty()) {
            for (MultipartFile file : videos) {
                Video video = new Video(
                        file.getOriginalFilename(),
                        file.getBytes(),
                        file.getContentType(),
                        file.getSize(),
                        imovel
                );
                imovel.getVideos().add(video);
            }
        }

        if (dto.getDocumentos() != null) {
            List<Long> keepDocIds = dto.getDocumentos().stream()
                    .map(DocumentoImovelDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();
            imovel.getDocumentos().removeIf(doc -> !keepDocIds.contains(doc.getId()));
        }

        if (documentos != null && !documentos.isEmpty()) {
            for (MultipartFile file : documentos) {
                DocumentoImovel doc = new DocumentoImovel(
                        file.getOriginalFilename(),
                        "outros",
                        file.getBytes(),
                        file.getContentType(),
                        file.getSize(),
                        imovel
                );
                imovel.getDocumentos().add(doc);
            }
        }

        Imovel savedImovel = imovelRepository.save(imovel);
        return imovelMapper.toDTO(savedImovel);
    }

    @Transactional
    public void delete(Long id) {
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));
        imovelRepository.delete(imovel);
    }

    @Transactional(readOnly = true)
    public Page<ImovelResponseDTO> findAllPaged(Pageable pageable, ImovelFilterDTO filters) {
        Long currentUserId = securityContextUtil.getCurrentUserIdAsLong();
        String currentUserRole = securityContextUtil.getCurrentUserRole();

        Specification<Imovel> spec = ImovelSpecification.withFilters(filters);

        if (securityContextUtil.isGerente()) {
            List<User> teamUsers = userRepository.findByGerenteId(currentUserId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(currentUserId);

            Specification<Imovel> scopeSpec = (root, query, cb) -> root.get("corretorId").in(teamIds);
            spec = spec.and(scopeSpec);
        } else if (currentUserRole.equals("CORRETOR")) {
            Specification<Imovel> scopeSpec = (root, query, cb) -> cb.equal(root.get("corretorId"), currentUserId);
            spec = spec.and(scopeSpec);
        }

        return imovelRepository.findAll(spec, pageable).map(imovelMapper::toDTO);
    }
}
