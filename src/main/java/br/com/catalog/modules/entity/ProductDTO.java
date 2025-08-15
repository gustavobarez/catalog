package br.com.catalog.modules.entity;

import java.math.BigDecimal;

public record ProductDTO(String title, String description, BigDecimal price, CategoryEntity categoryId, String ownerId) {
}
