package com.lipari.Academy2026.mapper;

import com.lipari.Academy2026.dto.CategoryRequestDTO;
import com.lipari.Academy2026.dto.CategoryResponseDTO;
import com.lipari.Academy2026.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // Da Entity a Risposta (per il client)
    CategoryResponseDTO toDto(CategoryEntity entity);

    // Da Richiesta a Entity (per creazione)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productsList", ignore = true)
    CategoryEntity toEntity(CategoryRequestDTO dto);

    // Lista di risposte
    List<CategoryResponseDTO> toDtoList(List<CategoryEntity> entities);

    // Aggiornamento entità da richiesta
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productsList", ignore = true)
    void updateEntityFromRequest(CategoryRequestDTO dto, @MappingTarget CategoryEntity entity);
}
