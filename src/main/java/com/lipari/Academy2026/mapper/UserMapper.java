package com.lipari.Academy2026.mapper;

import com.lipari.Academy2026.dto.UserResponseDTO;
import com.lipari.Academy2026.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "orders", ignore = true)
    UserResponseDTO toDto(UserEntity entity);
    UserEntity toEntity(UserResponseDTO dto);
    List<UserResponseDTO> toDtoList(List<UserEntity> entityList);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UserResponseDTO dto, @MappingTarget UserEntity entity);

}
