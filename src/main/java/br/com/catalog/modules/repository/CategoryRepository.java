package br.com.catalog.modules.repository;

import java.util.UUID;

import br.com.catalog.modules.entity.CategoryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<CategoryEntity> {

    public CategoryEntity findById(UUID id) {
        return find("id", id).firstResult();
    }

}
