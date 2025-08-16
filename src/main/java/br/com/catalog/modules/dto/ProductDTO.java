package br.com.catalog.modules.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(String title, String description, BigDecimal price, UUID categoryId, String ownerId) {
}
