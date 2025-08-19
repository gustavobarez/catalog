package br.com.catalog.catalog.category.application;

import java.util.UUID;

import br.com.catalog.catalog.category.domain.CategoryEntity;

public record CategoryResponseDTO(UUID id, String title, String description, String ownerId) {

    public CategoryResponseDTO(CategoryEntity categoryEntity) {
        this(categoryEntity.getId(), categoryEntity.getTitle(), categoryEntity.getDescription(),
                categoryEntity.getOwnerId());
    }

}
