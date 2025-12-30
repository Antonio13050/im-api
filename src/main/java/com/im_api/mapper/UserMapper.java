package com.im_api.mapper;

import com.im_api.dto.UserCreateDTO;
import com.im_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "senha", ignore = true) // Handled manually with encoding
    @Mapping(target = "roles", ignore = true) // Handled manually with repository lookup
    @Mapping(target = "createdDate", ignore = true)
    User toEntity(UserCreateDTO dto);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    void updateEntityFromDTO(UserCreateDTO dto, @MappingTarget User user);
}
