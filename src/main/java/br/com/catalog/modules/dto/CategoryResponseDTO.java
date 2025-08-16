package br.com.catalog.modules.dto;

import java.util.UUID;

import br.com.catalog.modules.entity.CategoryEntity;

public record CategoryResponseDTO(UUID id, String title, String description, String ownerId) {

    public CategoryResponseDTO(CategoryEntity categoryEntity) {
        this(categoryEntity.getId(), categoryEntity.getTitle(), categoryEntity.getDescription(),
                categoryEntity.getOwnerId());
    }

}
