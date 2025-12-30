package com.im_api.mapper;

import com.im_api.dto.ClienteDTO;
import com.im_api.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDTO toDTO(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "documentos", ignore = true) // Documentos usually handled separately or matched by name
    Cliente toEntity(ClienteDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "documentos", ignore = true)
    void updateEntityFromDTO(ClienteDTO dto, @MappingTarget Cliente cliente);
}
