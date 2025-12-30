package com.im_api.mapper;

import com.im_api.dto.ImovelDTO;
import com.im_api.dto.FotoDTO;
import com.im_api.dto.VideoDTO;
import com.im_api.model.Imovel;
import com.im_api.model.Foto;
import com.im_api.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface ImovelMapper {

    @Mapping(target = "fotos", source = "fotos", qualifiedByName = "mapFotoToDTO")
    @Mapping(target = "videos", source = "videos", qualifiedByName = "mapVideoToDTO")
    ImovelDTO toDTO(Imovel imovel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "fotos", ignore = true) // Handled manually in service for uploads
    @Mapping(target = "videos", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    Imovel toEntity(ImovelDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "videos", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    void updateEntityFromDTO(ImovelDTO dto, @MappingTarget Imovel imovel);

    @Named("mapFotoToDTO")
    default FotoDTO mapFotoToDTO(Foto foto) {
        if (foto == null) return null;
        return new FotoDTO(
            foto.getId(),
            foto.getTipoConteudo(),
            foto.getNomeArquivo(),
            foto.getDados() != null ? Base64.getEncoder().encodeToString(foto.getDados()) : null
        );
    }

    @Named("mapVideoToDTO")
    default VideoDTO mapVideoToDTO(Video video) {
        if (video == null) return null;
        return new VideoDTO(
            video.getId(),
            video.getTipoConteudo(),
            video.getNomeArquivo(),
            video.getDados() != null ? Base64.getEncoder().encodeToString(video.getDados()) : null,
            video.getTamanho()
        );
    }
}
