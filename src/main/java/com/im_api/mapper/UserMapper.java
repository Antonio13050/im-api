package com.im_api.mapper;

import com.im_api.dto.UserCreateDTO;
import com.im_api.dto.UserResponseDTO;
import com.im_api.dto.UserUpdateDTO;
import com.im_api.model.TipoContratacao;
import com.im_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "tipoContratacao", source = "tipoContratacao", qualifiedByName = "stringToTipoContratacao")
    User toEntity(UserCreateDTO dto);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "tipoContratacao", source = "tipoContratacao", qualifiedByName = "stringToTipoContratacao")
    void updateEntityFromDTO(UserUpdateDTO dto, @MappingTarget User user);

    @Mapping(target = "roles", expression = "java(mapRoles(user))")
    @Mapping(target = "tipoContratacao", source = "user.tipoContratacao", qualifiedByName = "tipoContratacaoToString")
    UserResponseDTO toResponseDTO(User user);

    default Set<String> mapRoles(User user) {
        if (user.getRoles() == null) return Set.of();
        return user.getRoles().stream()
                .map(role -> role.getNome())
                .collect(Collectors.toSet());
    }

    @Named("stringToTipoContratacao")
    default TipoContratacao stringToTipoContratacao(String value) {
        if (value == null || value.isBlank()) return null;
        return TipoContratacao.valueOf(value);
    }

    @Named("tipoContratacaoToString")
    default String tipoContratacaoToString(TipoContratacao tipo) {
        return tipo != null ? tipo.name() : null;
    }
}
