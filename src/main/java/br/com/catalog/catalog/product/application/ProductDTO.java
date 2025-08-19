package br.com.catalog.catalog.product.application;

import java.math.BigDecimal;

public record ProductDTO(String title, String description, BigDecimal price, String categoryId, String ownerId) {
}
