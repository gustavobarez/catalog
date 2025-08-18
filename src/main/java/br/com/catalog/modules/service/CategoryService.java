package br.com.catalog.modules.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.catalog.exceptions.BusinessRuleException;
import br.com.catalog.modules.dto.CategoryDTO;
import br.com.catalog.modules.dto.CategoryResponseDTO;
import br.com.catalog.modules.entity.CategoryEntity;
import br.com.catalog.modules.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoryService {

    @Inject
    public CategoryRepository categoryRepository;

    @Transactional
    public CategoryEntity insert(CategoryDTO dto) {
        CategoryEntity categoryEntity = new CategoryEntity(dto);
        categoryRepository.persist(categoryEntity);
        return categoryEntity;
    }

    @Transactional
    public CategoryResponseDTO update(String id, CategoryDTO dto) {
        CategoryEntity category = categoryRepository.findById(UUID.fromString(id));
        if (category == null) {
            throw new NotFoundException("Category not found!");
        }
        category.setTitle(dto.title());
        category.setDescription(dto.description());
        return new CategoryResponseDTO(category);
    }

    @Transactional
    public CategoryResponseDTO delete(String id) {
        CategoryEntity category = categoryRepository.findById(UUID.fromString(id));
        var dto = new CategoryResponseDTO(category);
        if (category == null) {
            throw new NotFoundException("Category not found!");
        }
        if (!category.getProducts().isEmpty()) {
            throw new BusinessRuleException("This category is being used by a product",
                    category.getProducts().toString());
        }
        return dto;
    }

    public List<CategoryResponseDTO> findAll() {
        var categories = categoryRepository.findAll().list();
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CategoryEntity> findById(String id) {
        var category = this.categoryRepository.findById(UUID.fromString(id));
        if (category == null) {
            throw new NotFoundException("Category not found!");
        }
        return Optional.of(category);
    }

}
