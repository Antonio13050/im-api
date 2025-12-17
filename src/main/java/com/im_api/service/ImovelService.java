package com.im_api.service;
import com.im_api.dto.ImovelDTO;
import com.im_api.dto.FotoDTO;
import com.im_api.dto.VideoDTO;
import com.im_api.dto.DocumentoImovelDTO;
import com.im_api.model.*;
import com.im_api.repository.ImovelRepository;
import com.im_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class ImovelService {
    private final ImovelRepository imovelRepository;
    private final UserRepository userRepository;
    public ImovelService(ImovelRepository imovelRepository, UserRepository userRepository) {
        this.imovelRepository = imovelRepository;
        this.userRepository = userRepository;
    }
    // Gerar código único para o imóvel
    private String gerarCodigoImovel() {
        Long count = imovelRepository.count() + 1;
        return String.format("IMV-%05d", count);
    }
    public ImovelDTO findById(Long id) {
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));
        return new ImovelDTO(imovel);
    }
    public Imovel create(ImovelDTO imovelDTO,
                         List<MultipartFile> fotos,
                         List<MultipartFile> videos,
                         List<MultipartFile> documentos) throws IOException {

        Imovel imovel = new Imovel(imovelDTO);

        // Gerar código automático se não fornecido
        if (imovel.getCodigo() == null || imovel.getCodigo().isBlank()) {
            imovel.setCodigo(gerarCodigoImovel());
        }
        // Processar fotos
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
        // Processar vídeos
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
        // Processar documentos
        if (documentos != null && !documentos.isEmpty()) {
            for (MultipartFile file : documentos) {
                DocumentoImovel doc = new DocumentoImovel(
                        file.getOriginalFilename(),
                        "outros", // tipo padrão
                        file.getBytes(),
                        file.getContentType(),
                        file.getSize(),
                        imovel
                );
                imovel.getDocumentos().add(doc);
            }
        }
        return imovelRepository.save(imovel);
    }
    public Imovel update(Long id, ImovelDTO dto,
                         List<MultipartFile> fotos,
                         List<MultipartFile> videos,
                         List<MultipartFile> documentos) throws IOException {

        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));
        // Atualizar campos básicos
        imovel.setTitulo(dto.getTitulo());
        imovel.setDescricao(dto.getDescricao());
        imovel.setTipo(dto.getTipo());
        imovel.setSubtipo(dto.getSubtipo());
        imovel.setFinalidade(dto.getFinalidade());
        imovel.setStatus(dto.getStatus());
        imovel.setDestaque(dto.getDestaque());
        imovel.setExclusividade(dto.getExclusividade());
        imovel.setEndereco(dto.getEndereco());

        // Áreas
        imovel.setAreaTotal(dto.getAreaTotal());
        imovel.setAreaConstruida(dto.getAreaConstruida());
        imovel.setAreaUtil(dto.getAreaUtil());
        imovel.setAnoConstrucao(dto.getAnoConstrucao());

        // Cômodos
        imovel.setQuartos(dto.getQuartos());
        imovel.setSuites(dto.getSuites());
        imovel.setBanheiros(dto.getBanheiros());
        imovel.setVagas(dto.getVagas());
        imovel.setVagasCobertas(dto.getVagasCobertas());
        imovel.setAndares(dto.getAndares());

        // Comodidades
        imovel.setComodidades(dto.getComodidades());

        // Financeiro
        imovel.setPrecoVenda(dto.getPrecoVenda());
        imovel.setPrecoAluguel(dto.getPrecoAluguel());
        imovel.setPrecoTemporada(dto.getPrecoTemporada());
        imovel.setValorCondominio(dto.getValorCondominio());
        imovel.setValorIptu(dto.getValorIptu());
        imovel.setValorEntrada(dto.getValorEntrada());
        imovel.setAceitaFinanciamento(dto.getAceitaFinanciamento());
        imovel.setAceitaFgts(dto.getAceitaFgts());
        imovel.setAceitaPermuta(dto.getAceitaPermuta());
        imovel.setPosseImediata(dto.getPosseImediata());
        imovel.setComissaoVenda(dto.getComissaoVenda());
        imovel.setComissaoAluguel(dto.getComissaoAluguel());

        // Documentação
        imovel.setSituacaoDocumental(dto.getSituacaoDocumental());
        imovel.setObservacoesInternas(dto.getObservacoesInternas());

        // Responsáveis
        imovel.setProprietarioId(dto.getProprietarioId());
        imovel.setInquilinoId(dto.getInquilinoId());
        imovel.setClienteId(dto.getClienteId());
        imovel.setCorretorId(dto.getCorretorId());
        // Manter fotos existentes
        if (dto.getFotos() != null) {
            List<Long> keepPhotoIds = dto.getFotos().stream()
                    .map(FotoDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();
            imovel.getFotos().removeIf(foto -> !keepPhotoIds.contains(foto.getId()));
        }
        // Adicionar novas fotos
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
        // Manter vídeos existentes
        if (dto.getVideos() != null) {
            List<Long> keepVideoIds = dto.getVideos().stream()
                    .map(VideoDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();
            imovel.getVideos().removeIf(video -> !keepVideoIds.contains(video.getId()));
        }
        // Adicionar novos vídeos
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
        // Manter documentos existentes
        if (dto.getDocumentos() != null) {
            List<Long> keepDocIds = dto.getDocumentos().stream()
                    .map(DocumentoImovelDTO::getId)
                    .filter(Objects::nonNull)
                    .toList();
            imovel.getDocumentos().removeIf(doc -> !keepDocIds.contains(doc.getId()));
        }
        // Adicionar novos documentos
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
        return imovelRepository.save(imovel);
    }
    public List<Imovel> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .map(role -> role.replace("SCOPE_", ""))
                .orElseThrow(() -> new RuntimeException("Usuário sem role"));
        if (currentUserRole.equals("ADMIN")) {
            return imovelRepository.findAll();
        } else if (currentUserRole.equals("GERENTE")) {
            Long gerenteId = Long.parseLong(currentUserId);
            List<User> teamUsers = userRepository.findByGerenteId(gerenteId);
            List<Long> teamIds = teamUsers.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            teamIds.add(gerenteId);
            return imovelRepository.findByCorretorIdIn(teamIds);
        } else {
            Long corretorId = Long.parseLong(currentUserId);
            return imovelRepository.findByCorretorId(corretorId);
        }
    }
    public void delete(Long id) {
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel com ID " + id + " não encontrado"));
        imovelRepository.delete(imovel);
    }
}