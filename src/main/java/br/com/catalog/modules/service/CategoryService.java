package br.com.catalog.modules.service;

import java.util.Optional;
import java.util.UUID;

import br.com.catalog.modules.dto.CategoryDTO;
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
    public CategoryEntity insert(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity(categoryDTO);

        categoryRepository.persist(categoryEntity);

        return categoryEntity;
    }

    public Optional<CategoryEntity> findById(UUID id) {
        return Optional.of(this.categoryRepository.findById(id));
    }

}
