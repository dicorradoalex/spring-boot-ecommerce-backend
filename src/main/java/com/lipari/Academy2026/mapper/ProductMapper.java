package com.lipari.Academy2026.mapper;

import com.lipari.Academy2026.dto.ProductRequestDTO;
import com.lipari.Academy2026.dto.ProductResponseDTO;
import com.lipari.Academy2026.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    // Da Entity a Risposta (per il client)
    ProductResponseDTO toDto(ProductEntity entity);

    // Da Richiesta a Entity (per creazione)
    // Ignoriamo category perché nel DTO abbiamo solo l'ID, la associazione avviene nel Service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    ProductEntity toEntity(ProductRequestDTO dto);

    // Lista di risposte
    List<ProductResponseDTO> toDtoList(List<ProductEntity> entityList);

    // Aggiornamento entità esistente da richiesta (da DTO)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntityFromRequest(ProductRequestDTO dto, @MappingTarget ProductEntity entity);
}
