package br.com.catalog.modules.dto;

import java.math.BigDecimal;

public record ProductDTO(String title, String description, BigDecimal price, String categoryId, String ownerId) {
}
