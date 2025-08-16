package br.com.catalog.modules.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.catalog.modules.entity.ProductEntity;

public record ProductResponseDTO(UUID id, String title, String description, BigDecimal price, UUID categoryId,
                String ownerId) {
        public ProductResponseDTO(ProductEntity productEntity) {
                this(productEntity.getId(), productEntity.getTitle(), productEntity.getDescription(),
                                productEntity.getPrice(), productEntity.getCategory().getId(),
                                productEntity.getOwnerId());
        }
}