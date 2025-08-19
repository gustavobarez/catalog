package br.com.catalog.catalog.product.application;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.catalog.catalog.product.domain.ProductEntity;

public record ProductResponseDTO(UUID id, String title, String description, BigDecimal price, UUID categoryId,
                String ownerId) {
        public ProductResponseDTO(ProductEntity productEntity) {
                this(productEntity.getId(), productEntity.getTitle(), productEntity.getDescription(),
                                productEntity.getPrice(), productEntity.getCategory().getId(),
                                productEntity.getOwnerId());
        }
}