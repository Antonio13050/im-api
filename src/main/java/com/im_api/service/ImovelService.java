package com.im_api.service;

import com.im_api.dto.ImovelDTO;
import com.im_api.model.Foto;
import com.im_api.dto.FotoDTO;
import com.im_api.model.Imovel;
import com.im_api.model.User;
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

    public Imovel create(ImovelDTO imovelDTO, List<MultipartFile> fotos) throws IOException {
        Imovel imovel = new Imovel(imovelDTO);

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

        if (imovelDTO.getFotos() != null) {
            List<Long> keepPhotoIds = imovelDTO.getFotos().stream()
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
