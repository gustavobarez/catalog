package br.com.catalog.modules.service;

import java.util.Optional;
import java.util.UUID;

import br.com.catalog.modules.dto.ProductDTO;
import br.com.catalog.modules.entity.ProductEntity;
import br.com.catalog.modules.repository.CategoryRepository;
import br.com.catalog.modules.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService {

    @Inject
    public ProductRepository productRepository;

    @Inject
    public CategoryRepository categoryRepository;

    @Transactional
    public ProductEntity insert(ProductDTO dto) {
        ProductEntity product = new ProductEntity(dto);
        var category = categoryRepository.findById(dto.categoryId());
        product.setCategory(category);
        productRepository.persist(product);
        return product;
    }

    public Optional<ProductEntity> findById(UUID id) {
        var product = productRepository.findById(id);
        return Optional.of(product);
    }

    public Optional<ProductEntity> findByTitle(String title) {
        var product = productRepository.findByTitle(title);
        return Optional.of(product);
    }

}
