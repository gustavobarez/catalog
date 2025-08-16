package br.com.catalog.modules.service;

import java.util.Optional;
import java.util.UUID;

import br.com.catalog.modules.dto.CategoryDTO;
import br.com.catalog.modules.dto.CategoryResponseDTO;
import br.com.catalog.modules.entity.CategoryEntity;
import br.com.catalog.modules.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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
        
        // if (category == null) {
        //     throw new
        // }

        category.setTitle(dto.title());
        category.setDescription(dto.description());

        return new CategoryResponseDTO(category);
    }

    @Transactional
    public CategoryResponseDTO delete(String id) {
        CategoryEntity category = categoryRepository.findById(UUID.fromString(id));
        var dto = new CategoryResponseDTO(category);
        categoryRepository.delete(category);
        return dto;
    }

    public Optional<CategoryEntity> findById(String id) {
        return Optional.of(this.categoryRepository.findById(UUID.fromString(id)));
    }

}
