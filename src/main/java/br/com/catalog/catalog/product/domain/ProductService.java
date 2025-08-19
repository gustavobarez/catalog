package br.com.catalog.catalog.product.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.catalog.catalog.category.domain.CategoryService;
import br.com.catalog.catalog.product.application.ProductDTO;
import br.com.catalog.catalog.product.application.ProductResponseDTO;
import br.com.catalog.notification.infrastructure.aws.CatalogSnsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ProductService {

    public ProductRepository productRepository;

    public CategoryService categoryService;

    public CatalogSnsService catalogSnsService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService,
            CatalogSnsService catalogSnsService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.catalogSnsService = catalogSnsService;
    }

    @Transactional
    public ProductEntity insert(ProductDTO dto) {
        ProductEntity product = new ProductEntity(dto);
        var category = categoryService.findById(dto.categoryId()).get();
        if (category == null) {
            throw new NotFoundException("Category not found!");
        }
        product.setCategory(category);
        productRepository.persist(product);
        catalogSnsService.publish(product.toString());
        return product;
    }

    @Transactional
    public ProductResponseDTO update(String id, ProductDTO dto) {
        ProductEntity product = productRepository.findById(UUID.fromString(id));
        if (product == null) {
            throw new NotFoundException("Product not found!");
        }
        product.setTitle(dto.title());
        product.setDescription(dto.description());
        catalogSnsService.publish(product.toString());
        return new ProductResponseDTO(product);
    }

    @Transactional
    public ProductResponseDTO delete(String id) {
        ProductEntity product = productRepository.findById(UUID.fromString(id));
        if (product == null) {
            throw new NotFoundException("Product not found!");
        }
        var dto = new ProductResponseDTO(product);
        productRepository.delete(product);
        return dto;
    }

    public List<ProductResponseDTO> findAll() {
        var products = productRepository.findAll().list();
        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ProductEntity> findById(String id) {
        var product = productRepository.findById(UUID.fromString(id));
        if (product == null) {
            throw new NotFoundException("Product not found!");
        }
        return Optional.of(product);
    }

    public Optional<ProductEntity> findByTitle(String title) {
        var product = productRepository.findByTitle(title);
        if (product == null) {
            throw new NotFoundException("Product not found!");
        }
        return Optional.of(product);
    }

}
